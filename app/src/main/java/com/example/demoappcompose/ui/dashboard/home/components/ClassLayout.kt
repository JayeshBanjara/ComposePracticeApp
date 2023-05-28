package com.example.demoappcompose.ui.dashboard.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import com.example.demoappcompose.R
import com.example.demoappcompose.ui.theme.Blue
import com.example.demoappcompose.ui.theme.LightBlue

@Composable
fun ClassLayout(
    classes: List<String>
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(10.dp),
        content = {
            items(classes) {
                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .height(48.dp)
                        .fillMaxWidth()
                        .border(BorderStroke(width = 1.dp, color = Blue), shape = RoundedCornerShape(10.dp))
                        .background(color = LightBlue, shape = RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = it,
                        style = TextStyle(
                            color = Blue,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.W700,
                            fontFamily = FontFamily(Font(R.font.quicksand_bold))
                        )
                    )
                }
            }
        })
}