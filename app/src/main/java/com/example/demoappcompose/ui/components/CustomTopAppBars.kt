package com.example.demoappcompose.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.demoappcompose.R
import com.example.demoappcompose.ui.theme.Blue
import com.example.demoappcompose.ui.theme.TitleColor
import com.example.demoappcompose.ui.theme.WhiteText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    title: String,
    showBack: Boolean = false,
    onBackClick: () -> Unit = {},
    actionIcon: Painter? = null,
    onIconClick: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        modifier = Modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Blue,
            titleContentColor = Color.White,
        ),
        navigationIcon = {
            if (showBack) {
                IconButton(onClick = { onBackClick() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null,
                        tint = WhiteText
                    )
                }
            }
        },
        title = {
            Text(
                text = title,
                style = TextStyle(
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.W700,
                    fontFamily = FontFamily(Font(R.font.quicksand_medium))
                )
            )
        },
        actions = {
            if (actionIcon != null) {
                IconButton(onClick = { onIconClick() }) {
                    Icon(
                        painter = actionIcon,
                        contentDescription = null,
                        tint = WhiteText
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WhiteTopAppBar(title: String) {
    CenterAlignedTopAppBar(
        modifier = Modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.White,
            titleContentColor = TitleColor,
        ),
        title = {
            Text(
                text = title,
                style = TextStyle(
                    color = TitleColor,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.W700,
                    fontFamily = FontFamily(Font(R.font.quicksand_medium))
                )
            )
        }
    )
}