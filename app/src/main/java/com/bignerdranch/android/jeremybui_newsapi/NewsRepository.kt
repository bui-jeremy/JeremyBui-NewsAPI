package com.bignerdranch.android.jeremybui_newsapi

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsRepository(private val newsApiService: NewsApiService) {
    suspend fun getTopHeadlines(country: String, apiKey: String, category: String): NewsResponse? {
        return withContext(Dispatchers.IO) {
            val response = newsApiService.getTopHeadlines(country, apiKey, category)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        }
    }
}
