package com.example.demoappcompose.ui.dashboard.my_subscription

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.demoappcompose.R
import com.example.demoappcompose.ui.components.CustomTopAppBar
import com.example.demoappcompose.ui.components.Loader
import com.example.demoappcompose.ui.screenPadding
import com.example.demoappcompose.utility.UiState

@Composable
fun MySubscription(
    navController: NavController,
    mySubscriptionViewModel: MySubscriptionViewModel
) {
    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = "My Subscription",
                showBack = true,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    ) { innerPadding ->
        Box {

            val context = LocalContext.current

            Image(
                painter = painterResource(id = R.drawable.screen_bg),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )

            Surface(
                modifier = Modifier
                    .padding(
                        start = screenPadding(),
                        top = innerPadding.calculateTopPadding(),
                        end = screenPadding(),
                        bottom = screenPadding()
                    )
            ) {

                val state by remember { mySubscriptionViewModel.uiState }.collectAsState()
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

                        val subscriptionList =
                            (state as UiState.Success).data.subscriptionData.subscriptionList

                        LazyColumn(
                            content = {
                                item { Spacer(modifier = Modifier.height(10.dp)) }
                                items(subscriptionList) {
                                    MySubsItem(subscription = it)
                                }
                            })
                    }
                }
            }
        }
    }
}