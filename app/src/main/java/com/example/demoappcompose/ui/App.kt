package com.example.demoappcompose.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.demoappcompose.ui.navigation.AppNavigation
import com.example.demoappcompose.ui.theme.AppTheme

@Composable
fun App(isLoggedIn: Boolean) {
    AppTheme(darkTheme = false) {
        val navController = rememberNavController()

        Scaffold { innerPadding ->
            AppNavigation(
                modifier = Modifier.padding(innerPadding),
                navController = navController,
                isLoggedIn = isLoggedIn
            )
        }
    }
}
