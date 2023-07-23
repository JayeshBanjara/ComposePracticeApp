package com.example.demoappcompose.ui.create_question

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.demoappcompose.R
import com.example.demoappcompose.ui.HorizontalSpacer
import com.example.demoappcompose.ui.VerticalSpacer
import com.example.demoappcompose.ui.components.CustomTopAppBar
import com.example.demoappcompose.ui.components.Loader
import com.example.demoappcompose.ui.components.ScreenBackground
import com.example.demoappcompose.ui.create_question.components.QuestionPreferenceCheckBox
import com.example.demoappcompose.ui.create_question.components.QuestionTitleDropDown
import com.example.demoappcompose.ui.create_question.components.WhiteTextField
import com.example.demoappcompose.ui.create_question.model.Section
import com.example.demoappcompose.ui.navigation.Screens
import com.example.demoappcompose.ui.popUpToTop
import com.example.demoappcompose.ui.screenPadding
import com.example.demoappcompose.ui.theme.AppLightGreen
import com.example.demoappcompose.ui.theme.Blue
import com.example.demoappcompose.ui.theme.LightBlue
import com.example.demoappcompose.ui.theme.TitleColor
import com.example.demoappcompose.utility.UiState
import com.example.demoappcompose.utility.toast
import com.google.gson.Gson
import kotlinx.coroutines.launch

@Composable
fun CreateQuestion(
    navController: NavController,
    viewModel: CreateQuestionViewModel,
    classId: String,
    className: String,
    subjectId: String,
    subjectName: String,
    mediumId: String,
    updatedSection: String?
) {

    val context = LocalContext.current

    LaunchedEffect(key1 = updatedSection) {
        if (updatedSection != null) {

            val updatedSectionX = Gson().fromJson(updatedSection, Section::class.java)

            viewModel.sectionList.find { it.sectionId == viewModel.activeSection }?.questions =
                updatedSectionX?.questions
        }
    }

    Scaffold(topBar = {
        CustomTopAppBar(
            title = "Generate Exam Paper",
            showBack = true,
            onBackClick = {
                navController.popBackStack()
            },
            actionIcon = painterResource(id = R.drawable.ic_print),
            onIconClick = {

                //Find of any question has null heading, if found we will return
                val heading = viewModel.sectionList.find { it.selectedHeading == null }

                //Find of any question has null marks, if found we will return
                val marks = viewModel.sectionList.find { it.marks.isNullOrEmpty() }

                //Find of any question has null questions, if found we will return
                val questions = viewModel.sectionList.find { it.questions.isNullOrEmpty() }

                if (heading != null) {
                    context.toast("Please add Heading")
                } else if (marks != null) {
                    context.toast("Please add Marks")
                } else if (questions != null) {
                    context.toast("Please add Questions")
                } else {



                    val paperDataStr = viewModel.prepareRequest(
                        classId = classId,
                        className = className,
                        subjectId = subjectId,
                        subjectName = subjectName,
                        mediumId = mediumId
                    )
                    navController.navigate(Screens.PrintSettings.withArgs(paperDataStr))
                }
            }
        )
    }, floatingActionButton = {
        ExtendedFloatingActionButton(
            onClick = {
                val newSection = Section(
                    hasSectionName = viewModel.sectionWiseCheckedState.value,
                    sectionName = viewModel.getSectionName(),
                    headingList = viewModel.headingList,
                    selectedHeading = viewModel.headingList[0],
                    marks = ""
                )

                viewModel.sectionList.add(newSection)
                viewModel.sectionList.sortBy { it.sectionName[0].code }
                viewModel.lastSectionName.value = newSection.sectionName[0].code
            },
            containerColor = Blue,
        ) {
            Text(
                text = "Add Question Title", style = TextStyle(
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W500,
                    fontFamily = FontFamily(Font(R.font.quicksand_medium))
                )
            )
        }
    }) { innerPadding ->

        val localFocusManager = LocalFocusManager.current
        val coroutineScope = rememberCoroutineScope()

        Box(modifier = Modifier.fillMaxSize()) {

            ScreenBackground()

            LaunchedEffect(Unit) {
                coroutineScope.launch {
                    viewModel.getHeadingList(
                        classId = classId, subjectId = subjectId
                    )
                }
            }

            val getHeadingState by remember { viewModel.getHeadingState }.collectAsStateWithLifecycle()
            when (getHeadingState) {
                is UiState.Empty -> {}
                is UiState.UnAuthorised -> {
                    LaunchedEffect(Unit) {
                        val errorMessage = (getHeadingState as UiState.UnAuthorised).errorMessage
                        context.toast(message = errorMessage)
                        navController.navigate(Screens.LoginScreen.route) {
                            popUpToTop(navController)
                        }
                    }
                }

                is UiState.Error -> {
                    val errorMessage = (getHeadingState as UiState.Error).errorMessage
                    LaunchedEffect(Unit) {
                        context.toast(message = errorMessage)
                    }
                }

                is UiState.Loading -> {
                    Loader()
                }

                is UiState.Success -> {

                    viewModel.headingList.clear()
                    viewModel.headingList =
                        (getHeadingState as UiState.Success).data.headingListData.headingList.toMutableList()

                    Column(
                        modifier = Modifier.padding(
                            start = screenPadding(),
                            top = innerPadding.calculateTopPadding(),
                            end = screenPadding(),
                            bottom = screenPadding()
                        )
                    ) {

                        VerticalSpacer(size = 8)

                        QuestionPreferenceCheckBox(text = "Section wise paper?",
                            checkedState = viewModel.sectionWiseCheckedState.value,
                            onCheckedChange = {
                                viewModel.sectionWiseCheckedState.value = it
                            })

                        VerticalSpacer(size = 3)

                        if (viewModel.sectionWiseCheckedState.value) {
                            QuestionPreferenceCheckBox(text = "Add new question in same section?",
                                checkedState = viewModel.newQueSameSectionCheckedState.value,
                                onCheckedChange = {
                                    viewModel.newQueSameSectionCheckedState.value = it
                                })
                        }

                        VerticalSpacer(size = 25)

                        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp), content = {

                            itemsIndexed(viewModel.sectionList) { index, section ->

                                var mExpanded by remember { mutableStateOf(false) }
                                var selectedHeading by remember { mutableStateOf(viewModel.headingList[0]) }
                                var marks by remember { mutableStateOf("") }

                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(
                                            color = LightBlue,
                                            shape = RoundedCornerShape(corner = CornerSize(5.dp))
                                        )
                                        .padding(8.dp)
                                ) {
                                    if (viewModel.sectionWiseCheckedState.value and section.hasSectionName) {
                                        Text(
                                            text = "Section ${section.sectionName}",
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

                                    Column(
                                        modifier = Modifier
                                            .wrapContentHeight()
                                            .fillMaxWidth()
                                    ) {
                                        Text(
                                            text = "Question Title", style = TextStyle(
                                                color = TitleColor,
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.W600,
                                                fontFamily = FontFamily(Font(R.font.quicksand_semi_bold))
                                            )
                                        )

                                        VerticalSpacer(size = 5)
                                        QuestionTitleDropDown(modifier = Modifier.fillMaxWidth(),
                                            mExpanded = mExpanded,
                                            items = viewModel.headingList,
                                            selectedHeading = section.selectedHeading
                                                ?: selectedHeading,
                                            onClick = { mExpanded = mExpanded.not() },
                                            onDismissRequest = { mExpanded = false },
                                            onItemSelect = {
                                                selectedHeading = it
                                                mExpanded = false
                                                section.selectedHeading = it
                                            })

                                        VerticalSpacer(size = 8)

                                        Row(
                                            modifier = Modifier
                                                .wrapContentHeight()
                                                .fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {

                                            WhiteTextField(modifier = Modifier.width(70.dp),
                                                text = section.marks ?: marks,
                                                placeholderText = "Marks",
                                                keyboardType = KeyboardType.NumberPassword,
                                                imeAction = ImeAction.Done,
                                                onNext = {
                                                    localFocusManager.moveFocus(FocusDirection.Down)
                                                },
                                                onValueChange = {
                                                    marks = it
                                                    viewModel.sectionList[index] =
                                                        section.copy(marks = it)
                                                })

                                            HorizontalSpacer(size = 10)

                                            if (section.questions != null) {
                                                section.questions?.forEachIndexed { index, questionData ->
                                                    Row(
                                                        modifier = Modifier
                                                            .width(100.dp)
                                                            .background(
                                                                color = AppLightGreen,
                                                                shape = RoundedCornerShape(5.dp)
                                                            )
                                                    ) {
                                                        Text(
                                                            text = (index + 1).toString(),
                                                            style = TextStyle(
                                                                color = TitleColor,
                                                                fontSize = 14.sp
                                                            )
                                                        )
                                                        IconButton(onClick = {
                                                            updatedSection.questions!!.removeAt(
                                                                index
                                                            )
                                                        }) {
                                                            Icon(
                                                                painter = painterResource(id = R.drawable.ic_close),
                                                                contentDescription = "Delete Question"
                                                            )
                                                        }
                                                    }
                                                }
                                            }


                                            HorizontalSpacer(size = 10)

                                            Row(
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .size(40.dp)
                                                        .background(
                                                            color = Blue,
                                                            shape = RoundedCornerShape(8.dp)
                                                        ), contentAlignment = Alignment.Center
                                                ) {
                                                    IconButton(
                                                        onClick = {
                                                            coroutineScope.launch {

                                                                viewModel.activeSection =
                                                                    viewModel.sectionList[index].sectionId

                                                                val sectionStr =
                                                                    Gson().toJson(section)

                                                                Log.e("Paper 1", sectionStr)

                                                                navController.navigate(
                                                                    Screens.ChapterList.withArgs(
                                                                        sectionStr,
                                                                        classId,
                                                                        subjectId,
                                                                        subjectName
                                                                    )
                                                                )
                                                            }
                                                        }, modifier = Modifier.size(30.dp)
                                                    ) {
                                                        Icon(
                                                            imageVector = Icons.Filled.Add,
                                                            contentDescription = null,
                                                            tint = Color.White
                                                        )
                                                    }
                                                }

                                                if (viewModel.sectionList.size > 1) {
                                                    HorizontalSpacer(size = 10)
                                                }

                                                if (viewModel.sectionList.size > 1) {

                                                    Box(
                                                        modifier = Modifier
                                                            .size(40.dp)
                                                            .background(
                                                                color = Blue,
                                                                shape = RoundedCornerShape(8.dp)
                                                            ), contentAlignment = Alignment.Center
                                                    ) {
                                                        IconButton(
                                                            onClick = {

                                                                viewModel.deletedSections.add(
                                                                    section.sectionName[0].toInt()
                                                                )

                                                                viewModel.sectionList.remove(
                                                                    section
                                                                )
                                                            }, modifier = Modifier.size(30.dp)
                                                        ) {
                                                            Icon(
                                                                imageVector = Icons.Filled.Delete,
                                                                contentDescription = null,
                                                                tint = Color.White
                                                            )
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        })
                    }
                }
            }
        }
    }
}