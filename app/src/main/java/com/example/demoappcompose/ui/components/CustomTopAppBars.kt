package com.example.demoappcompose.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.demoappcompose.R
import com.example.demoappcompose.ui.HorizontalSpacer
import com.example.demoappcompose.ui.theme.Blue
import com.example.demoappcompose.ui.theme.TitleColor
import com.example.demoappcompose.ui.theme.WhiteText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    title: String,
    showBack: Boolean = false,
    onBackClick: () -> Unit = {},
    imgUrl: String? = null,
    onImageClick: () -> Unit = {},
    actionIcon: Painter? = null,
    onIconClick: () -> Unit = {},
    questionCounts: Int? = null
) {
    CenterAlignedTopAppBar(modifier = Modifier.fillMaxWidth(),
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
                text = title, style = TextStyle(
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
                        painter = actionIcon, contentDescription = null, tint = WhiteText
                    )
                }
            }

            if (imgUrl != null) {
                Row {
                    AsyncImage(
                        model = imgUrl,
                        contentScale = ContentScale.Crop,
                        contentDescription = "User Profile Picture",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .border(
                                width = 0.5.dp,
                                color = Color.White,
                                shape = CircleShape
                            )
                            .clickable { onImageClick() }
                    )
                    HorizontalSpacer(size = 8)
                }
            }

            if (questionCounts != null) {
                Row {
                    Text(
                        text = "( $questionCounts )", style = TextStyle(
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.W500,
                            fontFamily = FontFamily(Font(R.font.quicksand_medium))
                        )
                    )
                    HorizontalSpacer(size = 10)
                }
            }
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WhiteTopAppBar(title: String) {
    CenterAlignedTopAppBar(modifier = Modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.White,
            titleContentColor = TitleColor,
        ),
        title = {
            Text(
                text = title, style = TextStyle(
                    color = TitleColor,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.W700,
                    fontFamily = FontFamily(Font(R.font.quicksand_medium))
                )
            )
        })
}