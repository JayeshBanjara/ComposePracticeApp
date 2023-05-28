package com.example.demoappcompose.ui.dashboard.bottom_nav

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.demoappcompose.ui.theme.White
import com.example.demoappcompose.ui.theme.WhiteText

@Composable
fun DashboardBottomNavigation(navController: NavController) {

    val items = listOf(
        BottomNavigationScreens.Home,
        BottomNavigationScreens.AboutUs,
        BottomNavigationScreens.ContactUs,
        BottomNavigationScreens.Feedback
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(75.dp)
            .background(WhiteText)
            .padding(horizontal = 10.dp)
    ) {
        items.forEach { item ->
            BottomMenuItem(
                item = item,
                isSelected = currentRoute == item.route
            ) {
                navController.navigate(item.route) {

                    navController.graph.startDestinationRoute?.let { home_screen ->
                        popUpTo(home_screen) {
                            saveState = true
                        }
                    }
                    launchSingleTop = true
                    restoreState = true
                }

            }
        }
    }
}