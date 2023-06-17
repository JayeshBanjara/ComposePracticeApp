package com.example.demoappcompose.ui.create_question

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.demoappcompose.R
import com.example.demoappcompose.ui.HorizontalSpacer
import com.example.demoappcompose.ui.VerticalSpacer
import com.example.demoappcompose.ui.components.CustomTopAppBar
import com.example.demoappcompose.ui.create_question.model.Chapter
import com.example.demoappcompose.ui.navigation.Screens
import com.example.demoappcompose.ui.screenPadding
import com.example.demoappcompose.ui.theme.Blue
import com.example.demoappcompose.ui.theme.LightBlue
import com.example.demoappcompose.ui.theme.NoRippleTheme
import com.example.demoappcompose.ui.theme.TitleColor

@Composable
fun ChapterList(navController: NavController, subjectName: String) {

    val list = mutableListOf<Chapter>()
    for (i in 1..10) {
        if (i == 1 || i == 2) {
            list.add(Chapter(isLocked = false))
        } else {
            list.add(Chapter(isLocked = true))
        }
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = subjectName,
                showBack = true,
                onBackClick = {
                    navController.popBackStack()
                },
                questionCounts = 5
            )
        }
    ) { innerPadding ->

        var showQRView by remember { mutableStateOf(false) }
        var clickedPos by remember { mutableIntStateOf(0) }

        CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
            Column(
                modifier = Modifier.padding(
                    start = screenPadding(),
                    top = innerPadding.calculateTopPadding(),
                    end = screenPadding(),
                    bottom = screenPadding()
                )
            ) {

                VerticalSpacer(size = 10)

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    content = {
                        itemsIndexed(list) { index, item ->
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(
                                        BorderStroke(width = 1.dp, color = Blue),
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .background(
                                        color = LightBlue,
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .padding(10.dp)
                                    .clickable {
                                        if (index < 2) {
                                            navController.navigate(Screens.QuestionList.withArgs("अध्याय ${index + 1} का नाम"))
                                        } else {
                                            showQRView = showQRView.not()
                                            clickedPos = index
                                            //item.isLocked = item.isLocked.not()
                                        }
                                    }
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .weight(1f),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        VerticalSpacer(size = 5)
                                        Text(
                                            text = "अध्याय ${index + 1}",
                                            style = TextStyle(
                                                color = Blue,
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.W700,
                                                fontFamily = FontFamily(Font(R.font.quicksand_bold)),
                                                textAlign = TextAlign.Center
                                            )
                                        )

                                        VerticalSpacer(size = 5)

                                        Text(
                                            text = "अध्याय ${index + 1} का नाम",
                                            style = TextStyle(
                                                color = Blue,
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.W700,
                                                fontFamily = FontFamily(Font(R.font.quicksand_semi_bold)),
                                                textAlign = TextAlign.Center
                                            )
                                        )

                                        VerticalSpacer(size = 5)
                                    }

                                    if (index > 1) {
                                        HorizontalSpacer(size = 5)
                                    }
                                    if (index > 1) {
                                        IconButton(onClick = {
                                                showQRView = showQRView.not()
                                                clickedPos = index
                                        }) {
                                            Icon(
                                                imageVector = Icons.Filled.Lock,
                                                contentDescription = null,
                                                tint = Blue,
                                                modifier = Modifier.size(50.dp)
                                            )
                                        }
                                    }
                                }

                                VerticalSpacer(size = 10)

                                AnimatedVisibility(visible = item.isLocked && clickedPos == index && showQRView) {
                                    Column(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        VerticalSpacer(size = 10)

                                        Image(
                                            painter = painterResource(id = R.drawable.ic_qr),
                                            contentDescription = null
                                        )

                                        VerticalSpacer(size = 10)

                                        Text(
                                            text = "Pay ₹ 500 /- to unlock your Unit",
                                            style = TextStyle(
                                                color = TitleColor,
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.W700,
                                                fontFamily = FontFamily(Font(R.font.quicksand_bold))
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    })
            }
        }
    }
}