package com.example.demoappcompose.ui.dashboard.contact_us

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.demoappcompose.R
import com.example.demoappcompose.ui.theme.TitleColor

@Composable
fun ContactUsHeader(text: String) {
    Text(
        text = "$text:",
        style = TextStyle(
            color = TitleColor,
            fontSize = 17.sp,
            fontWeight = FontWeight.W500,
            fontFamily = FontFamily(Font(R.font.quicksand_bold))
        ),
        modifier = Modifier
            .width(100.dp)
    )
}

@Composable
fun ContactUsValue(
    text: String,
    onClick: () -> Unit
) {
    Text(
        text = text,
        style = TextStyle(
            color = TitleColor,
            fontSize = 17.sp,
            fontWeight = FontWeight.W400,
            fontFamily = FontFamily(Font(R.font.quicksand_medium))
        ),
        textDecoration = TextDecoration.Underline,
        modifier = Modifier.clickable {
            onClick()
        }
    )
}

@Composable
fun ContactUsRow(
    headerText: String,
    valueText: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().wrapContentHeight()
    ) {
        ContactUsHeader(text = headerText)
        ContactUsValue(text = valueText, onClick = {
            onClick()
        })
    }
}