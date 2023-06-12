package com.example.demoappcompose.ui.dashboard.my_subscription

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.demoappcompose.ui.components.CustomTopAppBar
import com.example.demoappcompose.ui.screenPadding
import com.example.demoappcompose.ui.theme.LightBlue

@Composable
fun MySubscription(navController: NavController) {
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
        Surface(
            modifier = Modifier
                .padding(
                    start = screenPadding(),
                    top = innerPadding.calculateTopPadding(),
                    end = screenPadding(),
                    bottom = screenPadding()
                )
                .background(color = LightBlue)
        ) {
            LazyColumn(
                content = {
                    item { Spacer(modifier = Modifier.height(10.dp)) }
                    items(10) {
                        MySubsItem()
                    }
                })
        }
    }
}