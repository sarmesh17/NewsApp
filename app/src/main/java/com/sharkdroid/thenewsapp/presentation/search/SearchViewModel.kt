package com.sharkdroid.thenewsapp.presentation.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.sharkdroid.thenewsapp.domain.usecases.news.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private  val newsUseCases: NewsUseCases
): ViewModel() {

    // Mutable state to hold the current search state
    private val _state= mutableStateOf(SearchState())

    val state:State<SearchState>  = _state //public version

    /**
     * Function to handle incoming search events and update the state accordingly.
     *
     * @param event The search event to handle.
     */

    fun onEvent(event: SearchEvent){

        when(event){
            is SearchEvent.UpdateSearchQuery -> {
                // Update the search query in the state
                _state.value = state.value.copy(searchQuery = event.searchQuery)

            }
            is SearchEvent.SearchNews ->{
                // Trigger the search for news articles
                searchNews()

            }
        }

    }
    /**
     * Function to perform the actual search for news articles.
     * It updates the state with the search results.
     */
    private fun searchNews() {
        // Call the use case to search for news articles
        val articles=newsUseCases.searchNews(
            searchQuery = state.value.searchQuery,
            source = listOf("bbc-news","abc-news","al-jazeera-english")

        ).cachedIn(viewModelScope)
        // Update the state with the search results
        _state.value=state.value.copy(article=articles)
    }

}