package com.example.demoappcompose.ui.dashboard.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.demoappcompose.R
import com.example.demoappcompose.ui.theme.Blue

@Composable
fun CustomChip(
    name: String = "Chip",
    isSelected: Boolean = false,
    onSelectionChanged: () -> Unit = {},
) {
    Surface(
        tonalElevation = 8.dp,
        shape = RoundedCornerShape(50),
        color = if (isSelected) Blue else Color.White,
        border = BorderStroke(width = 1.dp, color = Blue)
    ) {
        Row(modifier = Modifier
            .padding(8.dp)
            .toggleable(
                value = isSelected,
                onValueChange = {
                    onSelectionChanged()
                }
            )
        ) {
            Text(
                text = name,
                style = TextStyle(
                    color = if (isSelected) Color.White else Blue,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W700,
                    fontFamily = FontFamily(Font(R.font.quicksand_bold))
                ),
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}