package com.bignerdranch.android.jeremybui_newsapi
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("v2/top-headlines")
    fun fetchTopHeadlines(@Query("country") country: String, @Query("apiKey") apiKey: String): Call<NewsResponse>
}

