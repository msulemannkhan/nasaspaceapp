package com.nasa.apollo11.adapters

import android.graphics.Color
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.nasa.apollo11.databinding.RowItemImagesBinding
import com.nasa.apollo11.models.Item

class ImagesAdapter(val callBack: (Item) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    Filterable {

    var imagesListFilterd = ArrayList<Item>()
    var searchText = ""

    // exampleListFull . exampleList
    var imagesList = mutableListOf<Item>()
    fun updateList(list: List<Item>) {
        imagesList.clear()
        imagesList.addAll(list)
        imagesListFilterd = list as ArrayList<Item>
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(imagesListFilterd[position]);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: RowItemImagesBinding =
            RowItemImagesBinding.inflate(layoutInflater, parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return imagesListFilterd.size
    }

    inner class ViewHolder(val binding: RowItemImagesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: Item) {
            val circularProgressDrawable = CircularProgressDrawable(binding.root.context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()
            Glide.with(binding.root.context).load(model.links[0].href)
                .placeholder(circularProgressDrawable).into(binding.imageView);
            binding.title.text = model.data[0].title

            val text = model.data[0].title // Your getter Method
            val htmlText =
                text.replace("$searchText", "<font color='#DA0B06'>$searchText</font>")
// Only searchText would be displayed in a different color.
            binding.title.text = Html.fromHtml(htmlText)

            binding.root.setOnClickListener {
                callBack.invoke(model)
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                searchText = charSearch
                if (charSearch.isEmpty()) {
                    imagesListFilterd = imagesList as ArrayList<Item>
                } else {
                    val resultList = ArrayList<Item>()
                    for (row in imagesList) {
                        if (row.data[0].title.toLowerCase()
                                .contains(constraint.toString().toLowerCase())
                        ) {
                            resultList.add(row)
                        }
                    }
                    imagesListFilterd = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = imagesListFilterd
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                imagesListFilterd = results?.values as ArrayList<Item>
                notifyDataSetChanged()
            }
        }
    }

}