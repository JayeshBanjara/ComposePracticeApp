package com.example.demoappcompose.ui.dashboard.home

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.demoappcompose.R
import com.example.demoappcompose.data.responses.dashboard_response.ClassData
import com.example.demoappcompose.data.responses.dashboard_response.MediumData
import com.example.demoappcompose.ui.VerticalSpacer
import com.example.demoappcompose.ui.components.Loader
import com.example.demoappcompose.ui.dashboard.home.components.ApplicationVisitor
import com.example.demoappcompose.ui.dashboard.home.components.ChipGroup
import com.example.demoappcompose.ui.dashboard.home.components.ClassLayout
import com.example.demoappcompose.ui.dashboard.home.components.LogoutPopup
import com.example.demoappcompose.ui.dashboard.home.components.MenusLayout
import com.example.demoappcompose.ui.navigation.Screens
import com.example.demoappcompose.ui.popUpToTop
import com.example.demoappcompose.ui.theme.TitleColor
import com.example.demoappcompose.utility.UiState
import com.example.demoappcompose.utility.toast
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel,
    modifier: Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        val context = LocalContext.current

        Image(
            painter = painterResource(id = R.drawable.screen_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

        val state by remember { homeViewModel.uiState }.collectAsStateWithLifecycle()
        when (state) {
            is UiState.Empty -> {}
            is UiState.Error -> {
                val errorMessage = (state as UiState.Error).data
                LaunchedEffect(Unit) {
                    Toast.makeText(
                        context,
                        errorMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            is UiState.Loading -> {
                Loader()
            }

            is UiState.Success -> {

                val dashboardData = (state as UiState.Success).data.dashboardData
                val classes = dashboardData.classList
                val mediums = dashboardData.mediumList
                val appVisitorCount = dashboardData.applicationVisitorCount.toString()

                MainContent(
                    modifier = modifier,
                    navController = navController,
                    homeViewModel = homeViewModel,
                    classes = classes,
                    mediums = mediums,
                    appVisitorCount = appVisitorCount,
                    context = context
                )
            }
        }
    }
}

@Composable
fun MainContent(
    modifier: Modifier,
    navController: NavController,
    homeViewModel: HomeViewModel,
    classes: List<ClassData>,
    mediums: List<MediumData>,
    appVisitorCount: String,
    context: Context
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        var selectedMedium by remember { mutableStateOf(mediums[0]) }
        val filteredClasses = classes.filter { it.mediumType in selectedMedium.mediumName }

        // State variable to control logout popup visibility
        var showLogoutPopup by remember { mutableStateOf(false) }

        ChipGroup(
            mediums = mediums,
            selectedMedium = selectedMedium,
            onSelectedChanged = {
                selectedMedium = it
            }
        )

        ClassLayout(classes = filteredClasses, navController)

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

        ApplicationVisitor(count = appVisitorCount)

        // Show logout popup if the state variable is true
        if (showLogoutPopup) {

            val coroutineScope = rememberCoroutineScope()

            val logoutState by remember { homeViewModel.logoutState }.collectAsStateWithLifecycle()
            when (logoutState) {
                is UiState.Empty -> {
                    LogoutPopup(
                        onLogoutConfirmed = {
                            coroutineScope.launch {
                                homeViewModel.logout()
                            }
                        },
                        onDismiss = { showLogoutPopup = false }
                    )
                }

                is UiState.Error -> {
                    val errorMessage = (logoutState as UiState.Error).data
                    LaunchedEffect(Unit) {
                        context.toast(errorMessage)
                    }
                }

                is UiState.Loading -> {
                    Loader()
                }

                is UiState.Success -> {
                    showLogoutPopup = false
                    navController.navigate(Screens.LoginScreen.route) {
                        popUpToTop(navController)
                    }
                }
            }
        }

    }
}