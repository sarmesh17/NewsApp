package com.sharkdroid.thenewsapp.presentation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.paging.compose.LazyPagingItems
import com.sharkdroid.thenewsapp.domain.model.Article
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sharkdroid.thenewsapp.R
import com.sharkdroid.thenewsapp.presentation.Dimension.mediumPadding1
import com.sharkdroid.thenewsapp.presentation.Dimension.mediumPadding2
import com.sharkdroid.thenewsapp.presentation.common.ArticleList
import com.sharkdroid.thenewsapp.presentation.common.SearchBar
import com.sharkdroid.thenewsapp.presentation.navgraph.Route

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    article: LazyPagingItems<Article>,
    navigateToSearch: () -> Unit,
    navigateToDetails: (Article) -> Unit
) {

    val title by remember {
        derivedStateOf {
            // Check if there are more than 10 articles
            if (article.itemCount > 10) {
                // Get the first 10 articles and extract their titles
                val firstTenTitles =
                    article.itemSnapshotList.items.slice(IntRange(start = 0, endInclusive = 9))
                        .map { it.title }
                // Join the titles with a separator to create a single string
                firstTenTitles.joinToString(separator = "\uD83d\uDFE5")
            } else {
                // If there are less than 10 articles, set title to empty string
                ""
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = mediumPadding1)
            .statusBarsPadding()
    ) {

        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = null,
            modifier = Modifier
                .width(150.dp)
                .height(30.dp)
                .padding(horizontal = mediumPadding1)
        )
        Spacer(modifier = Modifier.height(mediumPadding2))
        SearchBar(text = "",
            readOnly = true,
            onValueChange = {},
            onClick = {
                navigateToSearch()
            },
            onSearch = {})

        Spacer(modifier = Modifier.height(mediumPadding1))


        // search bar below
        Text(
            text = title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = mediumPadding1)
                .basicMarquee(),
            fontSize = 12.sp,
            color = colorResource(id = R.color.placeholder)
        )
        Spacer(modifier = Modifier.height(mediumPadding1))
        ArticleList(articles = article, modifier = Modifier.padding(horizontal = mediumPadding1),
            onClick = {
                navigateToDetails(it)
            }

        )
    }

}