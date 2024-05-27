package com.sharkdroid.thenewsapp.domain.repository

import androidx.paging.PagingData
import com.sharkdroid.thenewsapp.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getNews(source: List<String>):Flow<PagingData<Article>>

    fun searchNews(searchQuery:String,source: List<String>):Flow<PagingData<Article>>

    suspend fun upsertArticle(article: Article)

    suspend fun deleteArticle(article: Article)

    fun selectArticle():Flow<List<Article>>

    suspend fun selectArticle(url:String):Article?

}