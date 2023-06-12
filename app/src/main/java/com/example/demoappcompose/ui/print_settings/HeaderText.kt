package com.example.demoappcompose.ui.print_settings

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.demoappcompose.R
import com.example.demoappcompose.ui.theme.TitleColor

@Composable
fun HeaderText(text: String) {
    Text(
        text = text,
        style = TextStyle(
            color = TitleColor,
            fontSize = 15.sp,
            fontWeight = FontWeight.W600,
            fontFamily = FontFamily(Font(R.font.quicksand_semi_bold))
        )
    )
}