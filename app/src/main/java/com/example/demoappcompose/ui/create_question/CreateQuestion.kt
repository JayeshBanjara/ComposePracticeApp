package com.example.demoappcompose.ui.create_question

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.demoappcompose.R
import com.example.demoappcompose.ui.HorizontalSpacer
import com.example.demoappcompose.ui.VerticalSpacer
import com.example.demoappcompose.ui.components.CustomTextField
import com.example.demoappcompose.ui.components.CustomTopAppBar
import com.example.demoappcompose.ui.components.MainButton
import com.example.demoappcompose.ui.create_question.components.QuestionPreferenceCheckBox
import com.example.demoappcompose.ui.create_question.components.QuestionTitleDropDown
import com.example.demoappcompose.ui.create_question.components.QuestionTypeDropDown
import com.example.demoappcompose.ui.create_question.components.WhiteTextField
import com.example.demoappcompose.ui.navigation.Screens
import com.example.demoappcompose.ui.screenPadding
import com.example.demoappcompose.ui.theme.AppYellow
import com.example.demoappcompose.ui.theme.Blue
import com.example.demoappcompose.ui.theme.HintColor
import com.example.demoappcompose.ui.theme.LightBlue
import com.example.demoappcompose.ui.theme.TitleColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateQuestion(navController: NavController, subjectName: String) {
    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = "Generate Exam Paper",
                showBack = true,
                onBackClick = {
                    navController.popBackStack()
                },
                actionIcon = painterResource(id = R.drawable.ic_print),
                onIconClick = {
                    navController.navigate(Screens.PrintSettings.route)
                }
            )
        }
    ) { innerPadding ->

        var sectionWiseCheckedState by remember { mutableStateOf(false) }
        var questionSeriesCheckedState by remember { mutableStateOf(false) }
        var newQueSameSectionCheckedState by remember { mutableStateOf(false) }
        var questionNo by remember { mutableStateOf("") }
        var marks by remember { mutableStateOf("") }

        var mExpanded by remember { mutableStateOf(false) }
        val items = listOf("Select the most appropriate word having the nearest meaning.")
        var mSelectedText by remember { mutableStateOf(items[0]) }

        Column(
            modifier = Modifier.padding(
                start = screenPadding(),
                top = innerPadding.calculateTopPadding(),
                end = screenPadding(),
                bottom = screenPadding()
            )
        ) {

            val annotatedString = buildAnnotatedString {
                append("सूचना")
                withStyle(
                    style = SpanStyle(
                        color = HintColor,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400,
                        fontFamily = FontFamily(Font(R.font.quicksand_medium))
                    )
                ) {
                    append(": PDF में पेपर डाउनलोड करने के लिए केवल गूगल क्रोम का इस्तेमाल करें। अन्य ब्राउज़रों को अनइंस्टॉल करें।")
                }
            }

            VerticalSpacer(size = 8)

            Text(
                text = annotatedString,
                style = TextStyle(
                    color = TitleColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W700,
                    fontFamily = FontFamily(Font(R.font.quicksand_bold))
                )
            )

            VerticalSpacer(size = 15)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = AppYellow,
                        shape = RoundedCornerShape(corner = CornerSize(5.dp))
                    )
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_add_question),
                    contentDescription = "Add Question Title",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(35.dp)
                )

                HorizontalSpacer(size = 5)

                Text(
                    text = "Add Question Title",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W600,
                        fontFamily = FontFamily(Font(R.font.quicksand_semi_bold))
                    ),
                    modifier = Modifier.weight(1f)
                )

                /*HorizontalSpacer(size = 5)

                Image(
                    painter = painterResource(id = R.drawable.ic_save_question),
                    contentDescription = "Save Question",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(35.dp)
                )

                HorizontalSpacer(size = 5)

                Image(
                    painter = painterResource(id = R.drawable.ic_edit_question),
                    contentDescription = "Edit Question",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(34.dp)
                )*/
            }

            VerticalSpacer(size = 8)

            QuestionPreferenceCheckBox(
                text = "Section wise paper?",
                checkedState = sectionWiseCheckedState,
                onCheckedChange = {
                    sectionWiseCheckedState = it
                }
            )

            VerticalSpacer(size = 3)

            QuestionPreferenceCheckBox(
                text = "Question series?",
                checkedState = questionSeriesCheckedState,
                onCheckedChange = {
                    questionSeriesCheckedState = it
                }
            )

            VerticalSpacer(size = 3)

            if (sectionWiseCheckedState) {
                QuestionPreferenceCheckBox(
                    text = "Add new question in same section?",
                    checkedState = newQueSameSectionCheckedState,
                    onCheckedChange = {
                        newQueSameSectionCheckedState = it
                    }
                )
            }

            VerticalSpacer(size = 25)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = LightBlue,
                        shape = RoundedCornerShape(corner = CornerSize(5.dp))
                    )
            ) {
                Column {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = AppYellow,
                                shape = RoundedCornerShape(corner = CornerSize(5.dp))
                            )
                            .padding(8.dp)
                    ) {
                        if (sectionWiseCheckedState) {
                            Text(
                                text = "Section A",
                                style = TextStyle(
                                    color = Blue,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.W600,
                                    fontFamily = FontFamily(Font(R.font.quicksand_semi_bold))
                                ),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "प्रश्न सं.",
                                style = TextStyle(
                                    color = Color.White,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.W600,
                                    fontFamily = FontFamily(Font(R.font.quicksand_semi_bold))
                                )
                            )

                            HorizontalSpacer(size = 15)

                            Text(
                                text = "प्रश्न का नाम",
                                style = TextStyle(
                                    color = Color.White,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.W600,
                                    fontFamily = FontFamily(Font(R.font.quicksand_semi_bold))
                                ),
                                modifier = Modifier.weight(1f)
                            )

                            HorizontalSpacer(size = 5)

                            Text(
                                text = "अंक",
                                style = TextStyle(
                                    color = Color.White,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.W600,
                                    fontFamily = FontFamily(Font(R.font.quicksand_semi_bold))
                                )
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.Top
                    ) {

                        WhiteTextField(
                            modifier = Modifier
                                .width(50.dp)
                                .background(color = Color.White),
                            text = questionNo,
                            placeholderText = "Q1)",
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done,
                            onNext = { },
                            onValueChange = {
                                questionNo = it
                            }
                        )

                        HorizontalSpacer(size = 5)

                        QuestionTitleDropDown(
                            modifier = Modifier.weight(1f),
                            mExpanded = mExpanded ,
                            items = items,
                            mSelectedText = mSelectedText,
                            onClick = { mExpanded = mExpanded.not() },
                            onDismissRequest = {  mExpanded = false },
                            onItemSelect = {
                                mSelectedText = it
                                mExpanded = false
                            }
                        )

                        HorizontalSpacer(size = 5)

                        WhiteTextField(
                            modifier = Modifier
                                .width(80.dp)
                                .background(color = Color.White),
                            text = questionNo,
                            placeholderText = "100",
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done,
                            onNext = { },
                            onValueChange = {
                                questionNo = it
                            }
                        )

                    }
                }
            }

            VerticalSpacer(size = 10)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                MainButton(
                    modifier = Modifier.wrapContentWidth(),
                    text = "Add Question"
                ) {
                    navController.navigate(Screens.ChapterList.withArgs(subjectName))
                }
            }

            VerticalSpacer(size = 20)

            /* Box(
                 modifier = Modifier
                     .fillMaxWidth()
                     .background(
                         color = LightBlue,
                         shape = RoundedCornerShape(corner = CornerSize(5.dp))
                     )
             ) {

                 VerticalSpacer(size = 8)

                 LazyColumn(content = {
                     items(questionList) {
                         QuestionItem(questionData = it)
                     }
                 })

             }*/
        }
    }
}