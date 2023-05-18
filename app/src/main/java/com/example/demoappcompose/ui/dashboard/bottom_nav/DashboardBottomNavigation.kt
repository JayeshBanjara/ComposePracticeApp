package com.example.demoappcompose.ui.dashboard.bottom_nav

import androidx.compose.foundation.Image
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.demoappcompose.R

@Composable
fun DashboardBottomNavigation(navController: NavController) {

    val bottomNavItems = listOf(
        BottomNavItem(
            name = "Home",
            route = "home",
            icon = painterResource(id = R.drawable.ic_home),
        ),
        BottomNavItem(
            name = "Contact Us",
            route = "contact_us",
            icon = painterResource(id = R.drawable.ic_contact_us),
        ),
        BottomNavItem(
            name = "About Us",
            route = "about_us",
            icon = painterResource(id = R.drawable.ic_about_us),
        ),
        BottomNavItem(
            name = "Feedback",
            route = "feedback",
            icon = painterResource(id = R.drawable.ic_feedback),
        )
    )

    val backStackEntry = navController.currentBackStackEntryAsState()

    NavigationBar(
        containerColor = colorResource(id = R.color.teal_200),
        contentColor = Color.Black
    ) {
        bottomNavItems.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route

          NavigationBarItem(
              selected = selected,
              onClick = { navController.navigate(item.route) },
              icon = {
                  Image(painter = item.icon, contentDescription = "${item.name} Icon")
              }
          )
    }
}

}