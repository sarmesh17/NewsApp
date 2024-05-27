package com.sharkdroid.thenewsapp.domain.usecases.news

import androidx.paging.PagingData
import com.sharkdroid.thenewsapp.domain.model.Article
import com.sharkdroid.thenewsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class SearchNews(
    private val newsRepository: NewsRepository
) {

    operator fun  invoke (searchQuery: String, source:List<String>): Flow<PagingData<Article>> {

        return  newsRepository.searchNews(searchQuery=searchQuery,source=source)
    }
}