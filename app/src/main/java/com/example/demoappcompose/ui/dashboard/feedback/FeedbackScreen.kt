package com.example.demoappcompose.ui.dashboard.feedback

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.demoappcompose.R
import com.example.demoappcompose.ui.theme.TitleColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedbackScreen(navController: NavController, modifier: Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Feedback",
            fontWeight = FontWeight.Bold,
            color = TitleColor,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
    }
}