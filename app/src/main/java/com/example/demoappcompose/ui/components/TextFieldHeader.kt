package com.example.demoappcompose.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.demoappcompose.R
import com.example.demoappcompose.ui.theme.TitleColor

@Composable
fun TextFieldHeader(headerText: String) {
    Text(
        text = headerText,
        style = TextStyle(
            color = TitleColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.W600,
            fontFamily = FontFamily(Font(R.font.quicksand_medium))
        )
    )
}