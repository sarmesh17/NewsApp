package com.sharkdroid.thenewsapp.presentation.common

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sharkdroid.thenewsapp.R
import com.sharkdroid.thenewsapp.domain.model.Article
import com.sharkdroid.thenewsapp.presentation.Dimension
import com.sharkdroid.thenewsapp.presentation.Dimension.mediumPadding1
import com.sharkdroid.thenewsapp.ui.theme.TheNewsAppTheme

fun Modifier.shimmerEffect() = composed {
    // Create an infinite animation to continuously repeat the shimmer effect
    val transition = rememberInfiniteTransition()

    // Animate the alpha value (transparency) between 0.2 and 0.9 to create the shimmering effect
    val alpha = transition.animateFloat(
        initialValue = 0.2f, // Start partially transparent
        targetValue = 0.9f,  // End more opaque
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000), // Animate for 1 second
            repeatMode = RepeatMode.Reverse         // Reverse after reaching target
        )
    ).value

    // Set the background of the composable with the shimmering effect
    this.background(color = colorResource(id = R.color.shimmer).copy(alpha = alpha))
}

@Composable
fun ArticleCardShimmerEffect(
     modifier:Modifier=Modifier

){

    Row(modifier = modifier ) {

        Box(
            modifier = modifier
                .size(Dimension.ArticleCardSize)
                .clip(MaterialTheme.shapes.medium)
                .shimmerEffect()

        )

        Column(
            verticalArrangement = Arrangement.SpaceAround, modifier = Modifier
                .padding(horizontal = Dimension.ExtraSmallPadding)
                .height(
                    Dimension.ArticleCardSize
                )
        ) {
            Box(
                modifier = modifier
                    .shimmerEffect()
                    .fillMaxWidth()
                    .height(30.dp)
                    .padding(horizontal = mediumPadding1)

            )


            Row(verticalAlignment = Alignment.CenterVertically) {

                Box(
                    modifier = modifier
                        .fillMaxWidth(0.5f)
                        .height(15.dp)
                        .padding(horizontal = mediumPadding1)
                        .shimmerEffect()

                )

            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun Prev(){

    TheNewsAppTheme {
        ArticleCardShimmerEffect()

    }
}