package com.sharkdroid.thenewsapp.presentation.search

import androidx.paging.PagingData
import com.sharkdroid.thenewsapp.domain.model.Article
import kotlinx.coroutines.flow.Flow

data class SearchState(
    val searchQuery:String="", //searchQuery -> The current search query entered by the user. This string is used to filter articles based on the user's input.
    val article: Flow<PagingData<Article>>? = null // The flow of paginated articles resulting from the search query. It can be null if no search has been performed yet or if there are no results.

)
