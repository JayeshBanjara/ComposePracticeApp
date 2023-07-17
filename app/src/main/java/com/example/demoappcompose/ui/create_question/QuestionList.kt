package com.example.demoappcompose.ui.create_question

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.demoappcompose.ui.VerticalSpacer
import com.example.demoappcompose.ui.components.CustomTopAppBar
import com.example.demoappcompose.ui.components.Loader
import com.example.demoappcompose.ui.components.ScreenBackground
import com.example.demoappcompose.ui.create_question.components.QuestionItem
import com.example.demoappcompose.ui.create_question.components.QuestionTypeDropDown
import com.example.demoappcompose.ui.create_question.model.Section
import com.example.demoappcompose.ui.navigation.Screens
import com.example.demoappcompose.ui.popUpToTop
import com.example.demoappcompose.ui.screenPadding
import com.example.demoappcompose.utility.Constants
import com.example.demoappcompose.utility.UiState
import com.example.demoappcompose.utility.toast
import com.google.gson.Gson
import kotlinx.coroutines.launch

@Composable
fun QuestionList(
    navController: NavController,
    viewModel: QuestionListViewModel,
    chapterName: String,
    classId: String,
    subjectId: String,
    chapterId: String,
    section: String
) {

    LaunchedEffect(Unit) {

        val sectionX = Gson().fromJson(section, Section::class.java)

        if (sectionX != null) {
            if (sectionX.questions != null) {
                viewModel.selectedQuestions.addAll(sectionX.questions!!)
            }
        }
    }

    BackHandler {

        val sectionObj = Gson().fromJson(section, Section::class.java)
        sectionObj?.questions = viewModel.selectedQuestions
        val sectionStr = Gson().toJson(sectionObj)

        navController.previousBackStackEntry
            ?.savedStateHandle
            ?.set(Constants.QUESTIONS, sectionStr)
        navController.popBackStack()
    }

    val selectedQueCount = viewModel.selectedQueCount.collectAsStateWithLifecycle()

    Scaffold(topBar = {
        CustomTopAppBar(
            title = chapterName,
            showBack = true,
            onBackClick = {
                val sectionObj = Gson().fromJson(section, Section::class.java)
                sectionObj?.questions = viewModel.selectedQuestions
                val sectionStr = Gson().toJson(sectionObj)

                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set(Constants.QUESTIONS, sectionStr)
                navController.popBackStack()
            },
            questionCounts = selectedQueCount.value
        )
    }) { innerPadding ->

        var mExpanded by remember { mutableStateOf(false) }

        val context = LocalContext.current
        val coroutineScope = rememberCoroutineScope()

        Box(modifier = Modifier.fillMaxSize()) {

            ScreenBackground()

            LaunchedEffect(Unit) {

                viewModel.classId = classId
                viewModel.subjectId = subjectId
                viewModel.chapterId = chapterId

                coroutineScope.launch {
                    viewModel.getQuestionList()
                }
            }

            val state by remember { viewModel.uiState }.collectAsStateWithLifecycle()

            when (state) {
                is UiState.Empty -> {}
                is UiState.UnAuthorised -> {
                    LaunchedEffect(Unit) {
                        val errorMessage = (state as UiState.UnAuthorised).errorMessage
                        context.toast(message = errorMessage)
                        navController.navigate(Screens.LoginScreen.route) {
                            popUpToTop(navController)
                        }
                    }
                }

                is UiState.Error -> {
                    val errorMessage = (state as UiState.Error).errorMessage
                    LaunchedEffect(Unit) {
                        context.toast(message = errorMessage)
                    }
                }

                is UiState.Loading -> {
                    Loader()
                }

                is UiState.Success -> {

                    val questionTypeList =
                        (state as UiState.Success).data.questionListData.questionTypeList
                    var selectedQuestionType by remember { mutableStateOf(questionTypeList[0]) }

                    Column(
                        modifier = Modifier
                            .padding(
                                start = screenPadding(),
                                top = innerPadding.calculateTopPadding(),
                                end = screenPadding(),
                                bottom = screenPadding()
                            )
                            .fillMaxWidth()
                    ) {
                        VerticalSpacer(size = 10)

                        QuestionTypeDropDown(mExpanded = mExpanded,
                            items = questionTypeList,
                            selectedQuestionType = selectedQuestionType,
                            onClick = { mExpanded = mExpanded.not() },
                            onDismissRequest = { mExpanded = false },
                            onItemSelect = {
                                selectedQuestionType = it
                                mExpanded = false
                                viewModel.questionTypeId =
                                    selectedQuestionType.questionTypeId.toString()
                                coroutineScope.launch {
                                    viewModel.getQuestionList()
                                }
                            })

                        VerticalSpacer(size = 10)

                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(5.dp),
                            content = {
                                itemsIndexed(viewModel.questionList) { index, questionData ->
                                    QuestionItem(
                                        questionData = questionData,
                                        isSelected = questionData.isSelected,
                                        onSelect = {
                                            if (questionData.isSelected) {
                                                viewModel.removeQuestionSelection(
                                                    index = index,
                                                    questionData = questionData
                                                )
                                            } else {
                                                viewModel.setQuestionSelection(
                                                    index = index,
                                                    questionData = questionData
                                                )
                                            }
                                        })
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}