package com.example.demoappcompose.ui.create_question

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.demoappcompose.R
import com.example.demoappcompose.ui.VerticalSpacer
import com.example.demoappcompose.ui.components.CustomTopAppBar
import com.example.demoappcompose.ui.create_question.components.QuestionItem
import com.example.demoappcompose.ui.create_question.components.QuestionTypeDropDown
import com.example.demoappcompose.ui.create_question.model.QuestionData
import com.example.demoappcompose.ui.screenPadding

@Composable
fun QuestionList(navController: NavController, chapterName: String) {
    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = chapterName,
                showBack = true,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    ) { innerPadding ->

        var mExpanded by remember { mutableStateOf(false) }
        val items = listOf("MCQs", "1 Mark", "2 Mark")
        var mSelectedText by remember { mutableStateOf(items[0]) }
        var isMCQ by remember { mutableStateOf(mSelectedText == "MCQs")}

        val questionList = mutableListOf<QuestionData>()
        for (i in 1..10) {
            questionList.add(
                QuestionData(
                    isSelected = false,
                    question = null,
                    answers = null
                )
            )
        }

        Box(modifier = Modifier.fillMaxSize()) {

            Image(
                painter = painterResource(id = R.drawable.screen_bg),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )

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

                QuestionTypeDropDown(
                    mExpanded = mExpanded,
                    items = items,
                    mSelectedText = mSelectedText,
                    onClick = { mExpanded = mExpanded.not() },
                    onDismissRequest = { mExpanded = false },
                    onItemSelect = {
                        mSelectedText = it
                        mExpanded = false
                        isMCQ = mSelectedText == "MCQs"
                    }
                )

                VerticalSpacer(size = 10)

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    content = {
                        items(questionList) {
                            var isSelected by remember { mutableStateOf(it.isSelected) }

                            QuestionItem(
                                questionData = it,
                                isSelected = isSelected,
                                isMCQ = isMCQ,
                                onSelect = {
                                    isSelected = isSelected.not()
                                }
                            )
                        }
                    })
            }
        }
    }
}