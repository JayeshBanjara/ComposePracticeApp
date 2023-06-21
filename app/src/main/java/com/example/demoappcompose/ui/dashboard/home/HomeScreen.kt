package com.example.demoappcompose.ui.dashboard.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.demoappcompose.R
import com.example.demoappcompose.ui.VerticalSpacer
import com.example.demoappcompose.ui.dashboard.home.components.ApplicationVisitor
import com.example.demoappcompose.ui.dashboard.home.components.ChipGroup
import com.example.demoappcompose.ui.dashboard.home.components.ClassLayout
import com.example.demoappcompose.ui.dashboard.home.components.LogoutPopup
import com.example.demoappcompose.ui.dashboard.home.components.MenusLayout
import com.example.demoappcompose.ui.navigation.Screens
import com.example.demoappcompose.ui.popUpToTop
import com.example.demoappcompose.ui.theme.TitleColor

@Composable
fun HomeScreen(navController: NavController, modifier: Modifier) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Image(
            painter = painterResource(id = R.drawable.screen_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = modifier
                .fillMaxSize()
        ) {
            val mediums: List<String> = listOf("Hindi Medium", "Gujarati Medium", "English Medium")
            var selectedMedium by remember { mutableStateOf(mediums[0]) }
            val classes: List<String> = listOf("9", "10", "11", "12")

            // State variable to control logout popup visibility
            var showLogoutPopup by remember { mutableStateOf(false) }

            ChipGroup(
                mediums = mediums,
                selectedMedium = selectedMedium,
                onSelectedChanged = {
                    selectedMedium = it
                }
            )

            ClassLayout(classes = classes, navController)

            Text(
                text = stringResource(R.string.our_menus),
                style = TextStyle(
                    color = TitleColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W700,
                    fontFamily = FontFamily(Font(R.font.quicksand_bold))
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )

            VerticalSpacer(size = 10)

            MenusLayout(
                onItemClick = { menuTitle ->
                    when (menuTitle) {
                        "Profile" -> {
                            //openDialog = true
                            navController.navigate(Screens.EditProfile.route)
                        }

                        "My Subscription" -> {
                            navController.navigate(Screens.MySubscription.route)
                        }

                        "My Paper History" -> {
                            navController.navigate(Screens.PaperHistory.route)
                        }

                        "Register to purchase the book" -> {
                            navController.navigate(Screens.RegisterPurchaseBook.route)
                        }

                        "Logout" -> {
                            showLogoutPopup = true
                        }
                    }
                }
            )

            VerticalSpacer(size = 10)

            ApplicationVisitor()

            // Show logout popup if the state variable is true
            if (showLogoutPopup) {
                LogoutPopup(
                    onLogoutConfirmed = {
                        navController.navigate(Screens.LoginScreen.route) {
                            popUpToTop(navController)
                        }
                    },
                    onDismiss = { showLogoutPopup = false }
                )
            }

        }
    }
}