package com.example.demoappcompose.ui.payment

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.demoappcompose.ui.components.CustomTopAppBar
import com.example.demoappcompose.ui.components.ScreenBackground
import com.example.demoappcompose.ui.dashboard.my_subscription.MySubsItem
import com.example.demoappcompose.ui.screenPadding

@Composable
fun PaymentScreen() {
    Scaffold(topBar = {
        CustomTopAppBar(
            title = "Payment",
            showBack = true,
            onBackClick = {
              //  navController.popBackStack()
            }
        )
    }) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            ScreenBackground()

            Surface(
                modifier = Modifier
                    .padding(
                        start = screenPadding(),
                        top = innerPadding.calculateTopPadding(),
                        end = screenPadding(),
                        bottom = screenPadding()
                    )
            ) {
                LazyColumn(
                    content = {
                        item { Spacer(modifier = Modifier.height(10.dp)) }
                        items(10) {
                            MyPaymentItem()
                        }
                    })
            }
        }
    }
}