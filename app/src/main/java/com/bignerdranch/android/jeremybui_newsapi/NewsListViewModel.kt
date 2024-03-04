package com.bignerdranch.android.jeremybui_newsapi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers


class NewsListViewModel(private val repository: NewsRepository) : ViewModel() {
    private val _currentCategory = MutableLiveData<String>()

    // live data to update when we change spinner
    val articles: LiveData<List<Article>> = _currentCategory.switchMap { category ->
        liveData(Dispatchers.IO) {
            val headlines = repository.getTopHeadlines("us", "72ffe64459d24c979046f6ff1fbef5e9", category)
            val articlesList = if (headlines != null && headlines.articles != null) {
                headlines.articles
            } else {
                emptyList()
            }
            emit(articlesList)        }
    }

    init {
        _currentCategory.value = "business"
    }

    fun setCategory(category: String) {
        _currentCategory.value = category
    }
}
