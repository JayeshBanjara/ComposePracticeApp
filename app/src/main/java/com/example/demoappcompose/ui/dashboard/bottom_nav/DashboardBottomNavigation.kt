package com.example.demoappcompose.ui.dashboard.bottom_nav

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.demoappcompose.ui.theme.NoRippleTheme
import com.example.demoappcompose.ui.theme.WhiteText

@Composable
fun DashboardBottomNavigation(navController: NavController, userId: String) {

    val items = listOf(
        BottomNavigationScreens.Home,
        BottomNavigationScreens.AboutUs,
        BottomNavigationScreens.ContactUs,
        if (userId == "1") BottomNavigationScreens.PaymentScreen else BottomNavigationScreens.Feedback
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val context = LocalContext.current
    // Create the launcher for the activity result contract
    val sendEmailLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        // Handle the result of the email sending process if needed
    }

    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
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
                    if (item.route == BottomNavigationScreens.Feedback.route) {
                        //context.sendMail()

                        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:example@example.com")
                            putExtra(Intent.EXTRA_SUBJECT, "Hello!")
                            putExtra(Intent.EXTRA_TEXT, "This is the email body.")
                        }

                        // Create a custom chooser dialog
                        val chooserIntent = Intent.createChooser(emailIntent, "Send Email")

                        // Launch the chooser dialog via the activity result launcher
                        sendEmailLauncher.launch(chooserIntent)

                    } else {
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
    }
}