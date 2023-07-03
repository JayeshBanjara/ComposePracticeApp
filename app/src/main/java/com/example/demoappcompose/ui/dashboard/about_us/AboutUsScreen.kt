package com.example.demoappcompose.ui.dashboard.about_us

import android.webkit.WebView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.demoappcompose.R
import com.example.demoappcompose.data.PreferencesManager
import com.example.demoappcompose.ui.components.Loader
import com.example.demoappcompose.ui.navigation.Screens
import com.example.demoappcompose.ui.popUpToTop
import com.example.demoappcompose.utility.UiState
import com.example.demoappcompose.utility.toast

@Composable
fun AboutUsScreen(
    modifier: Modifier,
    navController: NavController,
    aboutUsViewModel: AboutUsViewModel
) {

    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.screen_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

        val state by remember { aboutUsViewModel.state }.collectAsStateWithLifecycle()
        when (state) {
            is UiState.Empty -> {}
            is UiState.UnAuthorised -> {
                LaunchedEffect(Unit) {
                    val errorMessage = (state as UiState.UnAuthorised).errorMessage
                    context.toast(message = errorMessage)
                    navController.navigate(Screens.LoginScreen.route) {
                        popUpToTop(navController)
                    }
                }
            }

            is UiState.Error -> {
                val errorMessage = (state as UiState.Error).errorMessage
                LaunchedEffect(Unit) {
                    context.toast(message = errorMessage)
                }
            }

            is UiState.Loading -> {
                Loader()
            }

            is UiState.Success -> {

                val htmlContent = (state as UiState.Success).data.aboutUsData.cmspage[0].pageContent

                Surface(modifier = modifier.fillMaxSize()) {
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
        }
    }
}