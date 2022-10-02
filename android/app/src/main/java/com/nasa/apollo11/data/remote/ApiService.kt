package com.nasa.apollo11.data.remote

import com.nasa.apollo11.models.AppliedFilter
import com.nasa.apollo11.models.Filters
import com.nasa.apollo11.models.ImagesModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ApiService {
    @GET("nivl_search?media_type=image")
    suspend fun getImages(): Response<ImagesModel>

    @GET("get_filters")
    suspend fun getFilters(): Response<Filters>

    @GET("apply_img_filter")
    suspend fun applyFilter(@QueryMap map: Map<String, String>): Response<AppliedFilter>
}
