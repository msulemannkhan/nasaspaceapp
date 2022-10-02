package com.nasa.apollo11.di

import android.content.Context
import androidx.navigation.ui.AppBarConfiguration
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nasa.apollo11.R
import com.nasa.apollo11.data.remote.ApiService
import com.nasa.apollo11.data.repo.AppRepository
import com.nasa.apollo11.dialogues.LoadingDialogue
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Singleton
    @Provides
    fun progressBar(@ApplicationContext context: Context): LoadingDialogue {
        return LoadingDialogue(context)
    }

    @Singleton
    @Provides
    fun provideRepository(
        apiService: ApiService,
        @ApplicationContext context: Context,
    ): AppRepository {
        return AppRepository(apiService, context)
    }

    @Provides
    @Singleton
    fun getAppBar(): AppBarConfiguration {
        return AppBarConfiguration(
            setOf(
                R.id.homeFragment
            )
        )
    }


    @Provides
    fun getGson(): Gson = GsonBuilder().create()


    private const val BASE_URL = "http://35.85.248.138:8000/"


    @Singleton
    @Provides
    fun provideOKHttpClient(): OkHttpClient {        // Create a trust manager that does not validate certificate chains
        return OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(15, TimeUnit.SECONDS) // write timeout
            .readTimeout(30, TimeUnit.SECONDS) // read timeout
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)


}