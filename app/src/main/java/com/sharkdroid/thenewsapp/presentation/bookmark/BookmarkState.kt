package com.sharkdroid.thenewsapp.presentation.bookmark

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.sharkdroid.thenewsapp.domain.model.Article

data class BookmarkState (
    val article:List<Article> = emptyList()
)