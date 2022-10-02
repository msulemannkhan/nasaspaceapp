package com.nasa.apollo11.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.nasa.apollo11.R
import com.nasa.apollo11.adapters.ImagesAdapter
import com.nasa.apollo11.databinding.FragmentHomeBinding
import com.nasa.apollo11.ui.MainViewModel
import com.nasa.apollo11.utils.onTextChange
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    val viewModel: MainViewModel by activityViewModels()
    lateinit var binding: FragmentHomeBinding
    lateinit var adapter: ImagesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ImagesAdapter {
            val directions = HomeFragmentDirections.actionHomeFragmentToDetailFragment(it)
            findNavController().navigate(directions)
        }
        binding.recyclerView.adapter = adapter
        viewModel.imagesLive.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = View.GONE
            adapter.updateList(it)
        }

        binding.search.etSearch onTextChange {
            adapter.filter.filter(it)

        }

    }
}