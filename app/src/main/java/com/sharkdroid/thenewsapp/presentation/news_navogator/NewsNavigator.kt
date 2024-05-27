package com.sharkdroid.thenewsapp.presentation.news_navogator

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.sharkdroid.thenewsapp.R
import com.sharkdroid.thenewsapp.domain.model.Article
import com.sharkdroid.thenewsapp.presentation.bookmark.BookmarkScreen
import com.sharkdroid.thenewsapp.presentation.bookmark.BookmarkViewModel
import com.sharkdroid.thenewsapp.presentation.details.DetailsEvent
import com.sharkdroid.thenewsapp.presentation.details.DetailsScreen
import com.sharkdroid.thenewsapp.presentation.details.DetailsViewModel
import com.sharkdroid.thenewsapp.presentation.home.HomeScreen
import com.sharkdroid.thenewsapp.presentation.home.HomeViewModel
import com.sharkdroid.thenewsapp.presentation.navgraph.Route
import com.sharkdroid.thenewsapp.presentation.news_navogator.components.BottomNavigationItem
import com.sharkdroid.thenewsapp.presentation.news_navogator.components.NewsBottomNavigation
import com.sharkdroid.thenewsapp.presentation.search.SearchScreen
import com.sharkdroid.thenewsapp.presentation.search.SearchViewModel

@Composable
fun NewsNavigator() {

    val bottomNavigationItem = remember {
        listOf(
            BottomNavigationItem(icon = R.drawable.ic_home, text = "Home"),
            BottomNavigationItem(icon = R.drawable.ic_search, text = "Search"),
            BottomNavigationItem(icon = R.drawable.ic_bookmark, text = "Bookmark")
        )
    }

    val navController = rememberNavController()
    /*
    currentBackStackEntryAsState(): This method returns a State object that represents the current back stack entry.
    The back stack is a list of all the screens that the user has visited in your app.
    The current back stack entry is the screen that the user is currently viewing.
     */
    val backstackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableIntStateOf(0)
    }

    selectedItem = when (backstackState?.destination?.route) {
            Route.HomeScreen.route -> 0
            Route.SearchScreen.route -> 1
            Route.BookmarkScreen.route -> 2
            else -> 0
        }
    //Hide the bottom navigation when the user is in the details screen
    val isBottomVisible = remember(key1 = backstackState) {
        backstackState?.destination?.route == Route.HomeScreen.route ||
                backstackState?.destination?.route == Route.SearchScreen.route ||
                backstackState?.destination?.route == Route.BookmarkScreen.route

    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {

            if (isBottomVisible) {


                NewsBottomNavigation(items = bottomNavigationItem,
                    selected = selectedItem, onItemClick = { index ->
                        when (index) {

                            0 -> navigateToTab(
                                navController = navController,
                                route = Route.HomeScreen.route
                            )

                            1 -> navigateToTab(
                                navController = navController,
                                route = Route.SearchScreen.route,
                            )

                            2 -> navigateToTab(
                                navController = navController,
                                route = Route.BookmarkScreen.route
                            )

                        }
                    })
            }
        }
    ) {
        val bottomPadding = it.calculateBottomPadding()
        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen.route,
            modifier = Modifier.padding(bottom = bottomPadding)
        )
        {
            composable(route = Route.HomeScreen.route) {
                val viewModel: HomeViewModel = hiltViewModel()
                val articles = viewModel.news.collectAsLazyPagingItems()
                HomeScreen(article = articles,
                    navigateToSearch = {
                        navigateToTab(
                            navController = navController,
                            route = Route.SearchScreen.route
                        )
                    },
                    navigateToDetails = { article ->

                        navigateToDetails(
                            navController = navController,
                            article = article
                        )

                    }
                )
            }
            composable(route = Route.SearchScreen.route) {
                val viewModel: SearchViewModel = hiltViewModel()
                val state = viewModel.state.value
                SearchScreen(
                    state = state,
                    event = viewModel::onEvent,
                    navigateToDetails = {article ->
                        navigateToDetails(
                            navController = navController,
                            article = article
                        )
                    }
                )
            }
            composable(route = Route.DetailScreen.route) {
                val viewModel: DetailsViewModel = hiltViewModel()
                if (viewModel.sideEffect != null) {
                    Toast.makeText(LocalContext.current, viewModel.sideEffect, Toast.LENGTH_SHORT).show()
                    viewModel.onEvent(DetailsEvent.RemoveSideEffect)
                }
                navController.previousBackStackEntry?.savedStateHandle?.get<Article?>("article")
                    ?.let { article ->
                        DetailsScreen(
                            article = article,
                            event = viewModel::onEvent,
                            navigateUp = { navController.navigateUp() })
                    }

            }
            composable(route = Route.BookmarkScreen.route) {
                val viewModel: BookmarkViewModel = hiltViewModel()
                val state = viewModel.state.value
                BookmarkScreen(state = state, navigateToDetails = { article ->

                    navigateToDetails(navController = navController, article = article)
                })
            }
        }

    }

}

// helperFunction
/*
The function navigateToTab is designed to navigate to a specified route while maintaining an efficient and user-friendly navigation stack.
It ensures that the app navigates to the specified route and pops up to the start destination if necessary, saving and restoring the state of destinations to preserve the UI state.
It avoids creating multiple instances of the same destination when it's already on top of the back stack.
 */
fun navigateToTab(navController: NavController, route: String) {

    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { homeScreen ->
            popUpTo(homeScreen) {
                saveState = true
            }
            restoreState = true
            launchSingleTop = true
        }
    }

}

private fun navigateToDetails(navController: NavController, article: Article) {
    Log.d("Navigation", "Navigating to details with article: $article")

    navController.currentBackStackEntry?.savedStateHandle?.set("article", article)
    navController.navigate(
        route = Route.DetailScreen.route
    )
}