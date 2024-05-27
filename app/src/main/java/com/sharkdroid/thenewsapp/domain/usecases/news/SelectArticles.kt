package com.sharkdroid.thenewsapp.domain.usecases.news

import com.sharkdroid.thenewsapp.data.local.NewsDao
import com.sharkdroid.thenewsapp.domain.model.Article
import com.sharkdroid.thenewsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class SelectArticles(
    private val newsRepository: NewsRepository
) {
     operator fun invoke():Flow<List<Article>>{
        return  newsRepository.selectArticle()
     }
}