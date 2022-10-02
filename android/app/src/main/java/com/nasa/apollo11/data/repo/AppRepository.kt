package com.nasa.apollo11.data.repo

import android.content.Context
import com.nasa.apollo11.data.remote.ApiService

import kotlinx.coroutines.*
import okhttp3.*
import java.util.*
import javax.inject.Inject


class AppRepository @Inject constructor(
    private val apiService: ApiService,
    private val context: Context
) {

    suspend fun getAllImages() = apiService.getImages()

    suspend fun getAllFilters() = apiService.getFilters()
    suspend fun applyFilter(map: Map<String, String>) = apiService.applyFilter(map)
}