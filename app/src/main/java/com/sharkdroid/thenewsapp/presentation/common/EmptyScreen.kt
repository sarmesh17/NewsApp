package com.sharkdroid.thenewsapp.presentation.common

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import com.sharkdroid.thenewsapp.R
import java.net.ConnectException
import java.net.SocketTimeoutException

@Composable
fun EmptyScreen(error: LoadState.Error? = null) {
    // State to hold the error message
    var message by remember {
        mutableStateOf(parseErrorMessage(error = error))
    }

    // State to hold the icon resource ID
    var icon by remember {
        mutableIntStateOf(R.drawable.ic_network_error)
    }

    // Set default message and icon if there is no error
    if (error == null){
        message = "You have not saved news so far !"
        icon = R.drawable.ic_search_document
    }

    // State to control the start of the animation
    var startAnimation by remember {
        mutableStateOf(false)
    }

    // Alpha animation for fading effect
    val alphaAnimation by animateFloatAsState(
        targetValue = if (startAnimation) 0.3f else 0f,
        animationSpec = tween(durationMillis = 1000)
    )

    // Launch the animation effect when the composable is composed
    LaunchedEffect(key1 = true) {
        startAnimation = true
    }

    // Display the content with animation
    EmptyContent(alphaAnim = alphaAnimation, message = message, iconId = icon)
}

@Composable
fun EmptyContent(alphaAnim: Float, message: String, iconId: Int) {
    // Column layout to center the icon and message
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Icon with conditional tint based on the theme
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = null,
            tint = if (isSystemInDarkTheme()) LightGray else DarkGray,
            modifier = Modifier
                .size(120.dp)
                .alpha(alphaAnim)
        )
        // Text message with padding and alpha animation
        Text(
            modifier = Modifier
                .padding(10.dp)
                .alpha(alphaAnim),
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = if (isSystemInDarkTheme()) LightGray else DarkGray,
        )
    }
}

// Function to parse error messages based on the type of exception
fun parseErrorMessage(error: LoadState.Error?): String {
    return when (error?.error) {
        is SocketTimeoutException -> {
            "Server Unavailable."
        }
        is ConnectException -> {
            "Internet Unavailable."
        }
        else -> {
            "Unknown Error."
        }
    }
}

// Preview function for the EmptyScreen composable
@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun EmptyScreenPreview() {
    EmptyContent(alphaAnim = 0.3f, message = "Internet Unavailable.",R.drawable.ic_network_error)
}
