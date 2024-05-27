package com.sharkdroid.thenewsapp.presentation.onBoarding.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.sharkdroid.thenewsapp.R
import com.sharkdroid.thenewsapp.presentation.Dimension.IndicatorSize
import com.sharkdroid.thenewsapp.ui.theme.BlueGray

@Composable
fun PageIndicator(
    modifier: Modifier = Modifier,
    pageSize: Int,
    selectedPage: Int,
    selectedColor: Color = Color.Blue,
    unSelectedColor:Color = BlueGray
) {

    Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween) {
        repeat(pageSize) {page ->

            Box(
                modifier = Modifier.size(IndicatorSize).clip(CircleShape)
                    .background(color = (if (page == selectedPage) selectedColor else unSelectedColor) as Color)
            ) {

            }
        }
    }

}