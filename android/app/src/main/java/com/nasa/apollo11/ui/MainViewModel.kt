package com.nasa.apollo11.ui

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nasa.apollo11.data.repo.AppRepository
import com.nasa.apollo11.models.AppliedFilter
import com.nasa.apollo11.models.FiltersItem
import com.nasa.apollo11.models.Item
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val context: Application,
    private val repository: AppRepository
) : AndroidViewModel(context) {
    val TAG = "MainViewModel"
    private val _imagesLive = MutableLiveData<List<Item>>()
    val imagesLive: LiveData<List<Item>> = _imagesLive


    private val _filters = MutableLiveData<List<FiltersItem>>()
    val filters: LiveData<List<FiltersItem>> = _filters

    fun getImages() {
        Log.i(TAG, "getImages: called")

        viewModelScope.launch {
            val res = repository.getAllImages()
            //    Log.i(TAG, "getImages: ${res.isSuccessful}")

            if (res.isSuccessful) {
                _imagesLive.postValue(res.body()?.collection?.items)
            }
        }
    }

    fun getFilters() {
        viewModelScope.launch {
            val res = repository.getAllFilters()
            if (res.isSuccessful) {
                _filters.postValue(res.body())
            }
        }
    }

    fun applyfilter(
        filter: FiltersItem,
        item: Item,
        callback: (Response<AppliedFilter>) -> Unit
    ) {

        viewModelScope.launch {
            val map: HashMap<String, String> = HashMap()
            map["filter_name"] = filter.name
            map["image_url"] = item.links[0].href
            val res = repository.applyFilter(map)
            callback.invoke(res)
            Log.i(TAG, "applyfilter: ${res.body()?.image_url ?: "Null"}")
        }
    }
}