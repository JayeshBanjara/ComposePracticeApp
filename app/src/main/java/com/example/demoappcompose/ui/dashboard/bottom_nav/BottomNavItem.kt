package com.example.demoappcompose.ui.dashboard.bottom_nav

import androidx.compose.ui.graphics.painter.Painter

data class BottomNavItem(
    val name: String,
    val route: String,
    val icon: Painter,
)