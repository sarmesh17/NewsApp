package com.sharkdroid.thenewsapp.presentation.onBoarding

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sharkdroid.thenewsapp.presentation.onBoarding.components.OnBoardingPage
import com.sharkdroid.thenewsapp.presentation.Dimension
import com.sharkdroid.thenewsapp.presentation.Dimension.mediumPadding2
import com.sharkdroid.thenewsapp.presentation.Dimension.pageIndicatorWidth
import com.sharkdroid.thenewsapp.presentation.common.NewsButton
import com.sharkdroid.thenewsapp.presentation.common.NewsTextButton
import com.sharkdroid.thenewsapp.presentation.onBoarding.components.PageIndicator
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(
    event: (OnBoardingEvent) -> Unit
) {

    Column(modifier = Modifier.fillMaxSize()) {
        val pagerState = rememberPagerState(initialPage = 0) {
            pages.size
        }
        val buttonState = remember {
            derivedStateOf {
                when (pagerState.currentPage) {
                    0 -> listOf("", "Next")
                    1 -> listOf("Back", "Get Started")
                    else -> listOf("", "")
                }
            }
        }

        HorizontalPager(state = pagerState) { index ->
            OnBoardingPage(page = pages[index])
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = mediumPadding2)
                .navigationBarsPadding(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            PageIndicator(
                modifier = Modifier.width(pageIndicatorWidth),
                pageSize = pages.size,
                selectedPage = pagerState.currentPage
            )


            Row(verticalAlignment = Alignment.CenterVertically) {
                val scope = rememberCoroutineScope()
                if (buttonState.value[0].isNotEmpty()) {
                    NewsTextButton(
                        text = buttonState.value[0], onClick = {
                            scope.launch {

                                pagerState.animateScrollToPage(page = pagerState.currentPage - 1)
                            }

                        }
                    )
                }
                NewsButton(text = buttonState.value[1], onClick = {
                    scope.launch {
                        if (pagerState.currentPage == 1) {
                            event(OnBoardingEvent.SaveAppEntry)
                        } else {

                            pagerState.animateScrollToPage(page = pagerState.currentPage + 1)
                        }
                    }
                })

            }
        }
        Spacer(modifier = Modifier.weight(0.5f))
    }
}
