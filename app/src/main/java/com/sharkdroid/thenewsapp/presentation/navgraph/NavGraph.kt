package com.sharkdroid.thenewsapp.presentation.navgraph

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.sharkdroid.thenewsapp.presentation.bookmark.BookmarkScreen
import com.sharkdroid.thenewsapp.presentation.bookmark.BookmarkViewModel
import com.sharkdroid.thenewsapp.presentation.home.HomeScreen
import com.sharkdroid.thenewsapp.presentation.home.HomeViewModel
import com.sharkdroid.thenewsapp.presentation.news_navogator.NewsNavigator
import com.sharkdroid.thenewsapp.presentation.onBoarding.OnBoardingScreen
import com.sharkdroid.thenewsapp.presentation.onBoarding.OnBoardingViewModel
import com.sharkdroid.thenewsapp.presentation.search.SearchScreen
import com.sharkdroid.thenewsapp.presentation.search.SearchViewModel

@Composable
fun NavGraph(startDestination: String) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination) {
        // AppStartNavigation graph
        navigation(
            route = Route.AppStartNavigation.route,
            startDestination = Route.OnBoardingScreen.route
        ) {
            composable(route = Route.OnBoardingScreen.route) {
                val viewModel: OnBoardingViewModel = hiltViewModel()
                OnBoardingScreen(event = viewModel::onEvent)
            }
        }

        // NewsNavigation graph
        navigation(
            route = Route.NewsNavigation.route,
            startDestination = Route.NewsNavigatorScreen.route
        ) {
            composable(route = Route.NewsNavigatorScreen.route) {

                NewsNavigator()

            }
        }
    }
}
