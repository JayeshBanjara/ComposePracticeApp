package com.example.demoappcompose.ui.dashboard.home

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
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
import com.example.demoappcompose.ui.dashboard.home.components.ChipGroup
import com.example.demoappcompose.ui.dashboard.home.components.ClassLayout
import com.example.demoappcompose.ui.dashboard.home.components.MenusLayout
import com.example.demoappcompose.ui.dashboard.home.components.ProfileDialog
import com.example.demoappcompose.ui.theme.TitleColor

@Composable
fun HomeScreen(navController: NavController, modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        val mediums: List<String> = listOf("Hindi Medium", "Gujarati Medium", "English Medium")
        var selectedMedium by remember { mutableStateOf(mediums[0]) }
        val classes: List<String> = listOf("12", "11", "10", "9", "8", "7", "6", "5", "4")
        var openDialog by remember { mutableStateOf(false) }

        if (openDialog) {
            ProfileDialog(
                onDismissRequest = {
                    openDialog = false
                }
            )
        }

        ChipGroup(
            mediums = mediums,
            selectedMedium = selectedMedium,
            onSelectedChanged = {
                selectedMedium = it
            }
        )

        ClassLayout(classes = classes, navController)

        VerticalSpacer(size = 10)

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
            onItemClick = {menuTitle ->
                when(menuTitle) {
                    "Profile" -> {
                        openDialog = true
                    }
                    "Logout" -> {

                    }
                }
            }
        )

    }
}