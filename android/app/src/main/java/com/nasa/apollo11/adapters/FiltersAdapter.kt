package com.nasa.apollo11.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.nasa.apollo11.databinding.RowItemFilterBinding
import com.nasa.apollo11.models.FiltersItem

class FiltersAdapter(val callBack: (FiltersItem) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var imagesListFilterd = ArrayList<FiltersItem>()

    fun updateList(list: List<FiltersItem>, itemOntop: FiltersItem) {
        imagesListFilterd.clear()
        imagesListFilterd.addAll(list)
        imagesListFilterd.add(0, itemOntop)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(imagesListFilterd[position]);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: RowItemFilterBinding =
            RowItemFilterBinding.inflate(layoutInflater, parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return imagesListFilterd.size
    }

    inner class ViewHolder(val binding: RowItemFilterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: FiltersItem) {
            val circularProgressDrawable = CircularProgressDrawable(binding.root.context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()
            Glide.with(binding.root.context).load(model.display_image)
                .placeholder(circularProgressDrawable).into(binding.imageView);
            binding.title.text = model.name.replace("_", " ")
            binding.root.setOnClickListener {
                callBack.invoke(model)
            }
        }
    }


}