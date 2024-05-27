package com.sharkdroid.thenewsapp.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.sharkdroid.thenewsapp.domain.model.Article
import com.sharkdroid.thenewsapp.presentation.Dimension.ExtraSmallPadding2
import com.sharkdroid.thenewsapp.presentation.Dimension.mediumPadding1

@Composable
fun ArticleList(
    modifier: Modifier = Modifier,
    articles: List<Article>,
    onClick: (Article) -> Unit
) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(mediumPadding1),
            contentPadding = PaddingValues(all = ExtraSmallPadding2)
        ) {
            items(count = articles.size) { index ->
                val article=articles[index]
                    // Display each article using the ArticleCard composable
                    ArticleCard(article = article) {
                        onClick(article)
                    }
                }
            }
        }









@Composable
fun ArticleList(
    modifier: Modifier = Modifier,
    articles: LazyPagingItems<Article>,
    onClick: (Article) -> Unit
) {
    // Check the paging state and handle accordingly
    val handlePagingResult = handlePagingResult(articles = articles)

    // If the data is loaded successfully, display the list of articles
    if (handlePagingResult) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(mediumPadding1),
            contentPadding = PaddingValues(all = ExtraSmallPadding2)
        ) {
            items(count = articles.itemCount) { index ->
                articles[index]?.let { article ->
                    // Display each article using the ArticleCard composable
                    ArticleCard(article = article) {
                        onClick(article) // single article sending
                    }
                }
            }
        }
    }
}

@Composable
fun handlePagingResult(
    articles: LazyPagingItems<Article>
): Boolean {
    // Get the current load state of the articles
    //The loadState property of LazyPagingItems provides information about the current loading state of the data.
    // It indicates whether the data is still loading, has been loaded successfully, or encountered an error during loading.
    val loadState = articles.loadState
    // Check for errors in different loading states (refresh, prepend, append)
    val error = when {
        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
        else -> null
    }

    return when {

        // If data is still loading, show a shimmer effect and return false
        loadState.refresh is LoadState.Loading -> {
            ShimmerEffect()
            false
        }
        // If there's an error, show the EmptyScreen composable and return false
        error != null -> {
            EmptyScreen(error = error)
            false
        }
        // If data is loaded successfully, return true
        else -> {
            true
        }
    }
}

@Composable
private fun ShimmerEffect() {
    // Display a column of shimmer effect placeholders
    Column(verticalArrangement = Arrangement.spacedBy(mediumPadding1)) {
        repeat(10) {
            ArticleCardShimmerEffect(
                modifier = Modifier.padding(horizontal = mediumPadding1)
            )
        }
    }
}
