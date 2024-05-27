package com.sharkdroid.thenewsapp.data.remote.dto

import com.sharkdroid.thenewsapp.domain.model.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)