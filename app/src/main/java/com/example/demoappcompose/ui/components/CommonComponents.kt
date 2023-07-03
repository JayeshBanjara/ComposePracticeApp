package com.example.demoappcompose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.demoappcompose.R
import com.example.demoappcompose.ui.navigation.Screens
import com.example.demoappcompose.ui.popUpToTop

@Composable
fun NavigateToLogin(navController: NavController) {
    navController.navigate(Screens.LoginScreen.route) {
        popUpToTop(navController)
    }
}

@Composable
fun Loader() {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ScreenBackground() {
    Image(
        painter = painterResource(id = R.drawable.screen_bg),
        contentDescription = null,
        modifier = Modifier.fillMaxSize()
    )
}