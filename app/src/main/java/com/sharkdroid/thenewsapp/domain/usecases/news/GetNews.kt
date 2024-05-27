package com.sharkdroid.thenewsapp.domain.usecases.news

import androidx.paging.PagingData
import com.sharkdroid.thenewsapp.domain.model.Article
import com.sharkdroid.thenewsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetNews(
    private val newsRepository:NewsRepository
) {
    operator fun invoke(sources: List<String>): Flow<PagingData<Article>>{
        return newsRepository.getNews(sources)  // calling from interface
                                                // data ready hai aur pagingdata return ho rha hai
    }
}

// paging data kya hai ?
// -> paging data ek container hai jo data ko hold ya store karta hai.