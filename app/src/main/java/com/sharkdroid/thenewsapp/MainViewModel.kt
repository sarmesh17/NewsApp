package com.sharkdroid.thenewsapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sharkdroid.thenewsapp.domain.usecases.app_entry.AppEntryUseCases
import com.sharkdroid.thenewsapp.presentation.navgraph.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    appEntryUseCases: AppEntryUseCases
) : ViewModel() {

    var splashCondition by mutableStateOf(true)
        private set
    var startDestination by mutableStateOf(Route.AppStartNavigation.route)
        private set

    init {
        appEntryUseCases.readAppEntry().onEach { shouldStartFromHomeScreen ->
            startDestination = if (shouldStartFromHomeScreen) {
                Route.NewsNavigation.route
            } else {
                Route.AppStartNavigation.route
            }
            delay(700)
            splashCondition = false
        }.launchIn(viewModelScope)
    }
}

//.launchIn() is like hiring a delivery person to keep you informed about new offerings.
// (viewModelScope) is like hiring the delivery person only for the restaurant's opening hours (while the ViewModel is active).
// Once you close the restaurant (the ViewModel is cleared), you don't need to know about new dishes anymore, so the delivery person is no longer needed.