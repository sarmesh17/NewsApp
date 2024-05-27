package com.sharkdroid.thenewsapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity
data class Article(

    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
   @PrimaryKey val url: String,
    val urlToImage: String
):Parcelable