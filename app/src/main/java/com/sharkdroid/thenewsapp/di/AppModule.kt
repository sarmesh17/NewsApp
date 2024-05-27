package com.sharkdroid.thenewsapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sharkdroid.thenewsapp.data.local.NewsDao
import com.sharkdroid.thenewsapp.data.local.NewsDataBase
import com.sharkdroid.thenewsapp.data.local.NewsTypeConverter
import com.sharkdroid.thenewsapp.data.manager.LocalUserManagerImp
import com.sharkdroid.thenewsapp.data.remote.NewsAPI
import com.sharkdroid.thenewsapp.data.repository.NewsRepositoryImp
import com.sharkdroid.thenewsapp.domain.manager.LocalUserManager
import com.sharkdroid.thenewsapp.domain.repository.NewsRepository
import com.sharkdroid.thenewsapp.domain.usecases.app_entry.AppEntryUseCases
import com.sharkdroid.thenewsapp.domain.usecases.app_entry.ReadAppEntry
import com.sharkdroid.thenewsapp.domain.usecases.app_entry.SaveAppEntry
import com.sharkdroid.thenewsapp.domain.usecases.news.DeleteArticle
import com.sharkdroid.thenewsapp.domain.usecases.news.GetNews
import com.sharkdroid.thenewsapp.domain.usecases.news.NewsUseCases
import com.sharkdroid.thenewsapp.domain.usecases.news.SearchNews
import com.sharkdroid.thenewsapp.domain.usecases.news.SelectArticle
import com.sharkdroid.thenewsapp.domain.usecases.news.SelectArticles
import com.sharkdroid.thenewsapp.domain.usecases.news.UpsertArticle
import com.sharkdroid.thenewsapp.util.Constant.BASE_URL
import com.sharkdroid.thenewsapp.util.Constant.NEWS_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocalUserManager(@ApplicationContext context: Context): LocalUserManager {

        return LocalUserManagerImp(context)
    }

    @Provides
    @Singleton
    fun provideAppEntryUseCases(localUserManager: LocalUserManager): AppEntryUseCases {

        return AppEntryUseCases(
            readAppEntry = ReadAppEntry(localUserManager),
            saveAppEntry = SaveAppEntry(localUserManager)
        )

    }

    @Provides
    @Singleton
    fun provideNewsApi(): NewsAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(newsAPI: NewsAPI,newsDao: NewsDao): NewsRepository {

        return NewsRepositoryImp(newsAPI,newsDao)
    }

    @Provides
    @Singleton
    fun provideNewsUseCases(newsRepository: NewsRepository, newsDao: NewsDao): NewsUseCases {

        return NewsUseCases(
            getNews = GetNews(newsRepository),
            searchNews = SearchNews(newsRepository),
            upsertArticle = UpsertArticle(newsRepository),
            deleteArticle = DeleteArticle(newsRepository),
            selectArticles = SelectArticles(newsRepository),
            selectArticle = SelectArticle(newsRepository)

        )

    }

    @Provides
    @Singleton
    fun provideNewsDatabase(
        application: Application
    ): NewsDataBase {

        return Room.databaseBuilder(
            context = application,
            klass = NewsDataBase::class.java,
            name = NEWS_DATABASE_NAME
        ).addTypeConverter(NewsTypeConverter())
            // Allow Room to drop all existing tables and recreate them if a migration path is missing.
            .fallbackToDestructiveMigration()
            .build()

    }

    @Provides
    @Singleton
    fun ProvidesNewsDao(
        newsDataBase: NewsDataBase
    ): NewsDao = newsDataBase.newsDao


}