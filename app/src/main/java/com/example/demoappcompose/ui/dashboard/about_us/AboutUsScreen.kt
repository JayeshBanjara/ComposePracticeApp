package com.example.demoappcompose.ui.dashboard.about_us

import android.webkit.WebView
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.demoappcompose.ui.screenPadding

@Composable
fun AboutUsScreen(navController: NavController, modifier: Modifier) {

    val htmlContent =
        "<h2>What is Lorem Ipsum?</h2><p><strong>Lorem Ipsum</strong>&nbsp;is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry&#39;s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</p>"

    Surface(modifier = modifier) {
        AndroidView(factory = {
            WebView(it).apply {
                loadDataWithBaseURL(
                    null,
                    htmlContent,
                    "text/html; charset=utf-8",
                    "utf8",
                    null
                )
            }
        })
    }
}