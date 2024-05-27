package com.sharkdroid.thenewsapp.presentation.search

// Sealed class representing different events that can occur in the search feature.

sealed class SearchEvent {

    /**
     * Event representing an update to the search query.
     *
     * @property searchQuery The new search query entered by the user.
     */
    data class UpdateSearchQuery(val searchQuery:String): SearchEvent()
    /**
     * Event representing the action of performing a search for news articles.
     */
    data object  SearchNews:SearchEvent()
}