package com.example.demoappcompose.ui.dashboard.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.demoappcompose.R
import com.example.demoappcompose.ui.navigation.Screens
import com.example.demoappcompose.ui.theme.Blue
import com.example.demoappcompose.ui.theme.LightBlue

@Composable
fun ClassLayout(
    classes: List<String>,
    navController: NavController
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(10.dp),
        content = {
            items(classes) {
                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .height(130.dp)
                        .fillMaxWidth()
                        .border(
                            BorderStroke(width = 1.dp, color = Blue),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .background(color = LightBlue, shape = RoundedCornerShape(10.dp))
                        .clickable {
                            navController.navigate(Screens.SubjectScreen.withArgs(it))
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = it,
                        style = TextStyle(
                            color = Blue,
                            fontSize = 35.sp,
                            fontWeight = FontWeight.W500,
                            fontFamily = FontFamily(Font(R.font.quicksand_semi_bold))
                        )
                    )
                }
            }
        })
}