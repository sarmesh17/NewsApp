package com.sharkdroid.thenewsapp.domain.usecases.news

import com.sharkdroid.thenewsapp.data.local.NewsDao
import com.sharkdroid.thenewsapp.domain.model.Article
import com.sharkdroid.thenewsapp.domain.repository.NewsRepository

class DeleteArticle(
    private val newsRepository: NewsRepository

) {
    suspend operator fun invoke(article: Article){

        newsRepository.deleteArticle(article)
    }

}