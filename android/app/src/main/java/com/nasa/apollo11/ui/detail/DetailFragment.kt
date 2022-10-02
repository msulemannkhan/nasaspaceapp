package com.nasa.apollo11.ui.detail

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.navArgs
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.nasa.apollo11.R
import com.nasa.apollo11.adapters.FiltersAdapter
import com.nasa.apollo11.databinding.FragmentDetailBinding
import com.nasa.apollo11.databinding.RowItemKeywordBinding
import com.nasa.apollo11.dialogues.LoadingDialogue
import com.nasa.apollo11.models.FiltersItem
import com.nasa.apollo11.ui.MainViewModel
import com.nasa.apollo11.utils.getLocalBitmapUri
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


@AndroidEntryPoint
class DetailFragment : Fragment() {
    lateinit var binding: FragmentDetailBinding
    val args: DetailFragmentArgs by navArgs()
    lateinit var adapter: FiltersAdapter
    val viewModel: MainViewModel by activityViewModels()

    lateinit var loading: LoadingDialogue


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    // Add menu items here
                    menuInflater.inflate(R.menu.share_menu, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    when (menuItem.itemId) {
                        android.R.id.home -> activity?.onBackPressed()
                        R.id.share -> shareImage()
                    }
                    return true

                }


            }, viewLifecycleOwner, Lifecycle.State.RESUMED
        )


        loading = LoadingDialogue(requireContext())
        binding.title.text = args.item.data[0].title
        val circularProgressDrawable = CircularProgressDrawable(binding.root.context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        adapter = FiltersAdapter {
            if (it.name == "Original")
                Glide.with(this).load(args.item.links[0].href)
                    .placeholder(circularProgressDrawable)
                    .into(binding.image)
            else {
                loading.show()
                viewModel.applyfilter(it, args.item) { appled ->
                    loading.dismiss()
                    Glide.with(this).load(appled.body()?.image_url ?: args.item.links[0].href)
                        .placeholder(circularProgressDrawable)
                        .into(binding.image)

                    Log.i("FilteredImage", "onViewCreated: ${appled.body()?.image_url}")
                    loading.dismiss()
                }
            }


        }
        binding.recyclerView.adapter = adapter


        Glide.with(this).load(args.item.links[0].href)
            .placeholder(circularProgressDrawable)
            .into(binding.image)


        viewModel.filters.observe(viewLifecycleOwner) {
            adapter.updateList(it, FiltersItem(args.item.links[0].href, "Original"))
        }



        args.item.data[0].keywords.forEach {
            val v = RowItemKeywordBinding.inflate(layoutInflater)
            v.title.text = it
            binding.rvKw.addView(v.root)
        }

        binding.desc.text = args.item.data[0].description
    }

    private fun shareImage() {
        val bmpUri = context?.let { binding.image.getLocalBitmapUri(it) }
        if (bmpUri != null) {
            // Construct a ShareIntent with link to image
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri)
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            shareIntent.putExtra(Intent.EXTRA_TEXT, args.item.data[0].description);

            shareIntent.type = "image/*"
            // Launch sharing dialog for image
            startActivity(Intent.createChooser(shareIntent, "Share Image"))
        } else {
            // user did'n get bitmap uri, sharing failed
            Toast.makeText(context, "Error in bitmap", Toast.LENGTH_SHORT).show()
        }
    }


}