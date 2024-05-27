package com.sharkdroid.thenewsapp.presentation.onBoarding

import androidx.annotation.DrawableRes
import com.sharkdroid.thenewsapp.R // Ensure you import the R class from your app's package


data class Page(
    val title:String,
    val description:String,
    @DrawableRes val image: Int
)

val pages= listOf(
    Page(
        title = "Get Informed, Stay Informed",
        description = "The world is full of stories. Get Informed, Stay Informed to be a part of the conversation.",
        image = R.drawable.onboarding2
    ),

    Page(
        title = "Get Informed, Stay Informed with Reliable News. Trusted Sources, Delivered Daily.",
        description = "Uncover the stories shaping our world. Trusted news, delivered daily",
        image = R.drawable.onboarding1
    )
)