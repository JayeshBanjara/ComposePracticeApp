package com.example.demoappcompose.ui.create_question

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.example.demoappcompose.data.responses.questions.HeadingData
import com.example.demoappcompose.ui.HorizontalSpacer
import com.example.demoappcompose.ui.VerticalSpacer
import com.example.demoappcompose.ui.components.CustomTopAppBar
import com.example.demoappcompose.ui.components.Loader
import com.example.demoappcompose.ui.components.ScreenBackground
import com.example.demoappcompose.ui.create_question.components.QuestionPreferenceCheckBox
import com.example.demoappcompose.ui.create_question.components.QuestionTitleDropDown
import com.example.demoappcompose.ui.create_question.components.WhiteTextField
import com.example.demoappcompose.ui.create_question.model.PaperData
import com.example.demoappcompose.ui.create_question.model.Section
import com.example.demoappcompose.ui.navigation.Screens
import com.example.demoappcompose.ui.popUpToTop
import com.example.demoappcompose.ui.screenPadding
import com.example.demoappcompose.ui.theme.Blue
import com.example.demoappcompose.ui.theme.LightBlue
import com.example.demoappcompose.ui.theme.TitleColor
import com.example.demoappcompose.utility.UiState
import com.example.demoappcompose.utility.toast
import kotlinx.coroutines.launch

@Composable
fun CreateQuestion(
    navController: NavController,
    createQuestionViewModel: CreateQuestionViewModel,
    classId: String,
    subjectId: String,
    subjectName: String
) {

    //val listState = rememberLazyListState()
    var headingList = mutableListOf<HeadingData>()
    val sectionList = remember { mutableStateListOf<Section>() }

    var sectionWiseCheckedState by rememberSaveable { mutableStateOf(false) }

    var newQueSameSectionCheckedState by rememberSaveable { mutableStateOf(false) }
    //var sectionCounter = "1"
    var lastSectionName = 65

    LaunchedEffect(Unit) {
        val section = Section(
            hasSectionName = true,
            sectionName = lastSectionName.toChar().toString(),
            headingList = headingList,
            selectedHeading = null,
            marks = ""
        )

        sectionList.add(section)

        val paperData = PaperData(
            isSectionWise = false,
            isAddNewInSameSection = false,
            lastSectionName = section.sectionName,
            sectionList = sectionList
        )
    }

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
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    val newSection = Section(
                        hasSectionName = sectionWiseCheckedState,
                        sectionName = if (sectionWiseCheckedState && !newQueSameSectionCheckedState) {
                            "${(lastSectionName + 1).toChar()}"
                        } else {
                            lastSectionName.toChar().toString()
                        },
                        headingList = headingList,
                        selectedHeading = headingList[0],
                        marks = ""
                    )

                    sectionList.add(newSection)
                    lastSectionName = newSection.sectionName[0].code
                },
                containerColor = Blue,
            ) {
                Text(
                    text = "Add Question Title",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W500,
                        fontFamily = FontFamily(Font(R.font.quicksand_medium))
                    )
                )
            }
        }
    ) { innerPadding ->

        val localFocusManager = LocalFocusManager.current
        val context = LocalContext.current
        val coroutineScope = rememberCoroutineScope()

        Box(modifier = Modifier.fillMaxSize()) {

            ScreenBackground()

            LaunchedEffect(Unit) {
                coroutineScope.launch {
                    createQuestionViewModel.getHeadingList(
                        classId = classId,
                        subjectId = subjectId
                    )
                }
            }

            val getHeadingState by remember { createQuestionViewModel.getHeadingState }.collectAsStateWithLifecycle()
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

                    headingList.clear()
                    headingList =
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

                        QuestionPreferenceCheckBox(
                            text = "Section wise paper?",
                            checkedState = sectionWiseCheckedState,
                            onCheckedChange = {
                                sectionWiseCheckedState = it
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

                        LazyColumn(
                            /*state = listState,*/
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            content = {
                                items(sectionList) { section ->

                                    var mExpanded by remember { mutableStateOf(false) }
                                    var selectedHeading by remember { mutableStateOf(headingList[0]) }
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
                                        if (sectionWiseCheckedState and section.hasSectionName) {
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
                                                text = "Question Title",
                                                style = TextStyle(
                                                    color = TitleColor,
                                                    fontSize = 14.sp,
                                                    fontWeight = FontWeight.W600,
                                                    fontFamily = FontFamily(Font(R.font.quicksand_semi_bold))
                                                )
                                            )

                                            VerticalSpacer(size = 5)
                                            QuestionTitleDropDown(
                                                modifier = Modifier.fillMaxWidth(),
                                                mExpanded = mExpanded,
                                                items = headingList,
                                                selectedHeading = selectedHeading,
                                                onClick = { mExpanded = mExpanded.not() },
                                                onDismissRequest = { mExpanded = false },
                                                onItemSelect = {
                                                    selectedHeading = it
                                                    mExpanded = false
                                                    section.selectedHeading = it
                                                }
                                            )

                                            VerticalSpacer(size = 8)

                                            Row(
                                                modifier = Modifier
                                                    .wrapContentHeight()
                                                    .fillMaxWidth(),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {

                                                WhiteTextField(
                                                    modifier = Modifier
                                                        .width(70.dp),
                                                    text = marks,
                                                    placeholderText = "Marks",
                                                    keyboardType = KeyboardType.NumberPassword,
                                                    imeAction = ImeAction.Done,
                                                    onNext = {
                                                        localFocusManager.moveFocus(FocusDirection.Down)
                                                    },
                                                    onValueChange = {
                                                        if (it.length < 5) {
                                                            marks = it
                                                            section.marks = it
                                                        }
                                                    }
                                                )

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
                                                            ),
                                                        contentAlignment = Alignment.Center
                                                    ) {
                                                        IconButton(
                                                            onClick = {
                                                                navController.navigate(
                                                                    Screens.ChapterList.withArgs(
                                                                        subjectName
                                                                    )
                                                                )
                                                            },
                                                            modifier = Modifier.size(30.dp)
                                                        ) {
                                                            Icon(
                                                                imageVector = Icons.Filled.Add,
                                                                contentDescription = null,
                                                                tint = Color.White
                                                            )
                                                        }
                                                    }

                                                    if (sectionList.size > 0) {
                                                        HorizontalSpacer(size = 10)
                                                    }

                                                    if (sectionList.size > 0) {

                                                        Box(
                                                            modifier = Modifier
                                                                .size(40.dp)
                                                                .background(
                                                                    color = Blue,
                                                                    shape = RoundedCornerShape(8.dp)
                                                                ),
                                                            contentAlignment = Alignment.Center
                                                        ) {
                                                            IconButton(
                                                                onClick = {
                                                                    sectionList.remove(section)
                                                                    lastSectionName -= 1
                                                                },
                                                                modifier = Modifier.size(30.dp)
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