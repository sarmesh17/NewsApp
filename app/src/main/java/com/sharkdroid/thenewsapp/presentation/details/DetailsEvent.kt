package com.sharkdroid.thenewsapp.presentation.details

import com.sharkdroid.thenewsapp.domain.model.Article

sealed class DetailsEvent {
    data class UpsertDeleteArticle(val article: Article): DetailsEvent()

    object RemoveSideEffect: DetailsEvent()

}