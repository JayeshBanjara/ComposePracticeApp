package com.example.demoappcompose.ui.create_question

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.demoappcompose.R
import com.example.demoappcompose.ui.VerticalSpacer
import com.example.demoappcompose.ui.components.Loader
import com.example.demoappcompose.ui.components.ScreenBackground
import com.example.demoappcompose.ui.create_question.components.QuestionItem
import com.example.demoappcompose.ui.create_question.components.QuestionTypeDropDown
import com.example.demoappcompose.ui.create_question.model.Section
import com.example.demoappcompose.ui.navigation.Screens
import com.example.demoappcompose.ui.popUpToTop
import com.example.demoappcompose.ui.screenPadding
import com.example.demoappcompose.utility.UiState
import com.example.demoappcompose.utility.getSerializable
import com.example.demoappcompose.utility.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class QuestionListActivity : AppCompatActivity() {

    private lateinit var txtTitle: TextView
    private lateinit var txtCount: TextView
    private lateinit var imgBack: ImageView
    private lateinit var composeView: ComposeView

    private lateinit var classId: String
    private lateinit var subjectId: String
    private lateinit var chapterId: String
    private lateinit var chapterName: String
    private var section: Section? = null

    private val viewModel by viewModels<QuestionListViewModel>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_list)

        classId = intent.getStringExtra("classId") ?: ""
        subjectId = intent.getStringExtra("subjectId") ?: ""
        chapterId = intent.getStringExtra("chapterId") ?: ""
        chapterName = intent.getStringExtra("chapterName") ?: ""
        section = intent.getSerializable("section", Section::class.java)

        viewModel.selectedQuestions.clear()
        viewModel.selectedQuestions.addAll(section!!.questions!!)

        viewModel.classId = classId
        viewModel.subjectId = subjectId
        viewModel.chapterId = chapterId

        txtTitle = findViewById(R.id.txt_title)
        txtCount = findViewById(R.id.txt_count)
        imgBack = findViewById(R.id.img_back)
        composeView = findViewById(R.id.compose_view)

        imgBack.setOnClickListener { handleBackClick() }
        txtTitle.text = chapterName
        txtCount.text = "(${section!!.questions?.size})"

        composeView.setContent {
            var mExpanded by remember { mutableStateOf(false) }

            val context = LocalContext.current
            val coroutineScope = rememberCoroutineScope()

            Box(modifier = Modifier.fillMaxSize()) {

                ScreenBackground()

                LaunchedEffect(Unit) {

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
                            /*navController.navigate(Screens.LoginScreen.route) {
                                popUpToTop(navController)
                            }*/
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
                                    /*start = screenPadding(),
                                    top = innerPadding.calculateTopPadding(),
                                    end = screenPadding(),
                                    bottom = screenPadding()*/
                                    screenPadding()
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

                                                //section!!.questions?.find { it.qId == questionData.qId } != null

                                                if (questionData.isSelected) {
                                                    viewModel.removeQuestionSelection(
                                                        index = index,
                                                        questionData = questionData
                                                    )
                                                    section!!.questions?.remove(questionData)

                                                    txtCount.text = "(${section!!.questions?.size})"

                                                } else {
                                                    section!!.questions?.add(questionData)
                                                    txtCount.text = "(${section!!.questions?.size})"
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

    override fun onBackPressed() {
        handleBackClick()
    }

    private fun handleBackClick() {

        section!!.questions = viewModel.selectedQuestions

        val i = Intent()
        i.putExtra("section", section)
        setResult(Activity.RESULT_OK, i)
        finish()
    }
}