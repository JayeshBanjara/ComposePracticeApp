package com.example.demoappcompose.ui.dashboard.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.demoappcompose.R
import com.example.demoappcompose.ui.theme.Blue
import com.example.demoappcompose.ui.theme.TitleColor

@Composable
fun LogoutPopup(
    onLogoutConfirmed: () -> Unit,
    onDismiss: () -> Unit
) {
    // Popup UI implementation
    // This can include text, buttons, or any other desired components

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Logout",
                style = TextStyle(
                    color = TitleColor,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W700,
                    fontFamily = FontFamily(Font(R.font.quicksand_bold))
                )
            )
        },
        text = {
            Text(
                text = "Are you sure you want to log out?",
                style = TextStyle(
                    color = TitleColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W500,
                    fontFamily = FontFamily(Font(R.font.quicksand_semi_bold))
                )
            )
        },
        confirmButton = {
            Button(
                onClick = onLogoutConfirmed,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Blue,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Logout",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W400,
                        fontFamily = FontFamily(Font(R.font.quicksand_medium))
                    )
                )
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Blue
                ),
                border = BorderStroke(
                    width = 1.dp,
                    color = Blue
                )
            ) {
                Text(
                    text = "Cancel",
                    style = TextStyle(
                        color = Blue,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W400,
                        fontFamily = FontFamily(Font(R.font.quicksand_medium))
                    )
                )
            }
        }
    )
}
