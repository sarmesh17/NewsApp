package com.sharkdroid.thenewsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.sharkdroid.thenewsapp.ui.theme.TheNewsAppTheme
import dagger.hilt.android.AndroidEntryPoint
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.sharkdroid.thenewsapp.data.local.NewsDao
import com.sharkdroid.thenewsapp.domain.model.Article
import com.sharkdroid.thenewsapp.domain.model.Source
import com.sharkdroid.thenewsapp.presentation.navgraph.NavGraph
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  private  val viewModel by viewModels<MainViewModel>()

    @Inject
    lateinit var dao:NewsDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Extend content behind system bars, handle insets manually
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Install splash screen
        installSplashScreen().apply {
            setKeepOnScreenCondition{
                //The setKeepOnScreenCondition method is used to keep the splash screen visible until a certain condition is met.
                viewModel.splashCondition
            }
        }
        setContent {
            TheNewsAppTheme {
                    val isSystemInDarkMode= isSystemInDarkTheme()
                    val systemController= rememberSystemUiController()

                SideEffect {
                    systemController.setSystemBarsColor(
                        color = Color.Transparent,
                        darkIcons = !isSystemInDarkMode
                    )
                }


                Box (modifier=Modifier.background(color=MaterialTheme.colorScheme.background)){
                    val startDestination=viewModel.startDestination
                    NavGraph(startDestination = startDestination)

                }


            }
        }
    }
}
