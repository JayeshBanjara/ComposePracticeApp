package com.example.demoappcompose.ui.components

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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
        shape = RoundedCornerShape(corner = CornerSize(5.dp)),
        modifier = modifier
    ) {
        Text(text = text)
    }
}