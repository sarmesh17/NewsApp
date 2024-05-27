package com.sharkdroid.thenewsapp.presentation.onBoarding.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sharkdroid.thenewsapp.presentation.onBoarding.Page
import com.sharkdroid.thenewsapp.presentation.Dimension.mediumPadding1
import com.sharkdroid.thenewsapp.presentation.Dimension.mediumPadding2
import com.sharkdroid.thenewsapp.R // Ensure you import the R class from your app's package
import com.sharkdroid.thenewsapp.presentation.onBoarding.pages
import com.sharkdroid.thenewsapp.ui.theme.TheNewsAppTheme


@Composable
fun OnBoardingPage(

    modifier: Modifier = Modifier,
    page: Page
) {

    Column(modifier = modifier) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f),
            painter = painterResource(id = page.image),
            contentDescription = null,
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.height(mediumPadding1))

        // title
        Text(
            text = page.title,
            modifier.padding(horizontal = mediumPadding2),
            style = MaterialTheme.typography.displayMedium,
            color = colorResource(id = R.color.display_small),
            fontSize = 20.sp,
            lineHeight = 23.sp,
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = page.description,
            modifier.padding(horizontal = mediumPadding2),
            style = MaterialTheme.typography.bodyMedium,
            color = colorResource(id = R.color.text_medium),
            fontSize = 16.sp
        )

    }

}
