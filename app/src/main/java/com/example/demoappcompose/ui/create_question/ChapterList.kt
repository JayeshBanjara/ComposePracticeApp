package com.example.demoappcompose.ui.create_question

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.demoappcompose.ui.VerticalSpacer
import com.example.demoappcompose.ui.components.CustomDropDown
import com.example.demoappcompose.ui.components.CustomTopAppBar
import com.example.demoappcompose.ui.create_question.components.QuestionTypeDropDown
import com.example.demoappcompose.ui.navigation.Screens
import com.example.demoappcompose.ui.screenPadding
import com.example.demoappcompose.ui.theme.Blue
import com.example.demoappcompose.ui.theme.LightBlue

@Composable
fun ChapterList(navController: NavController, subjectName: String) {
    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = subjectName,
                showBack = true,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    ) { innerPadding ->



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
                    items(10) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(
                                    BorderStroke(width = 1.dp, color = Blue),
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .background(color = LightBlue, shape = RoundedCornerShape(10.dp))
                                .padding(10.dp)
                                .clickable {
                                    navController.navigate(Screens.QuestionList.withArgs("अध्याय ${it + 1} का नाम"))
                                },
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            VerticalSpacer(size = 5)
                            Text(
                                text = "अध्याय ${it + 1}",
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
                                text = "अध्याय ${it + 1} का नाम",
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
                    }
            })
        }
    }
}