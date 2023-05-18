package com.example.demoappcompose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.demoappcompose.ui.theme.Blue

/**
 * Created by Jayesh Banjara on 05/05/23.
 */

@Composable
fun MainButton(
    modifier: Modifier,
    text: String,
    onClick: () -> Unit,
) {
    Button(
        onClick = { onClick() },
        modifier = modifier,
        shape = RoundedCornerShape(corner = CornerSize(5.dp)),
        colors = ButtonDefaults.buttonColors(
            containerColor = Blue, // Change the background color here
            contentColor = Color.White // Change the text color here
        )
    ) {
        Text(text = text)
    }
}