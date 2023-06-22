package com.example.demoappcompose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.demoappcompose.R

@Composable
fun ScreenBackground() {
    Image(
        painter = painterResource(id = R.drawable.screen_bg),
        contentDescription = null,
        modifier = Modifier.fillMaxSize()
    )
}