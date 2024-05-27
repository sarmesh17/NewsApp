package com.sharkdroid.thenewsapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sharkdroid.thenewsapp.data.local.NewsDao
import com.sharkdroid.thenewsapp.data.remote.NewsAPI
import com.sharkdroid.thenewsapp.data.remote.NewsPagingSource
import com.sharkdroid.thenewsapp.data.remote.SearchNewsPagingSource
import com.sharkdroid.thenewsapp.domain.model.Article
import com.sharkdroid.thenewsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

class NewsRepositoryImp(
    private val newsApi:NewsAPI,
    private val newsDao: NewsDao

):NewsRepository {
    override fun getNews(source: List<String>): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 10 ),
            pagingSourceFactory={
                NewsPagingSource(newsApi,
                    source=source.joinToString(separator = ",")
                )
            }
        ).flow
    }

    override fun searchNews(searchQuery: String, source: List<String>): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 10 ),
            pagingSourceFactory={
              SearchNewsPagingSource(newsApi,
                  searchQuery=searchQuery,
                  source=source.joinToString(separator = ",")
                  )
            }
        ).flow
    }

    override suspend fun upsertArticle(article: Article) {

        newsDao.upsert(article)

    }

    override suspend fun deleteArticle(article: Article) {
        newsDao.delete(article)
    }

    override fun selectArticle(): Flow<List<Article>> {

        return  newsDao.getArticles()
    }

    override suspend fun selectArticle(url: String): Article? {
       return newsDao.getArticle(url)
    }


}