package com.sharkdroid.thenewsapp.data.remote

import com.sharkdroid.thenewsapp.data.remote.dto.NewsResponse
import com.sharkdroid.thenewsapp.util.Constant.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    @GET("everything")
    suspend fun getNews(
        @Query("page") page: Int,
        @Query("sources") sources: String,
        @Query("apikey") apiKey: String = API_KEY
    ): NewsResponse

    @GET("everything")
    suspend fun searchNews(
        @Query("q") searchQuery: String,
        @Query("page") page: Int,
        @Query("sources") sources: String,
        @Query("apikey") apiKey: String = API_KEY
    ):NewsResponse
}