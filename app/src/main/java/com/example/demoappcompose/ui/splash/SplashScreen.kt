package com.example.demoappcompose.ui.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.demoappcompose.R
import com.example.demoappcompose.ui.components.popUpToTop
import com.example.demoappcompose.ui.navigation.Screens

@Composable
fun SplashScreen(navController: NavController) {

    val scale = remember {
        Animatable(0.1f)
    }

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.7f,
            animationSpec = tween(
                durationMillis = 3000/*,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                }*/
            )
        )
        //delay(3000L)
        navController.navigate(Screens.LoginScreen.route) {
            popUpToTop(navController = navController)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(
                id = R.drawable.ic_app_logo
            ),
            contentDescription = "Logo",
            modifier = Modifier.scale(scale.value)
        )
    }
}