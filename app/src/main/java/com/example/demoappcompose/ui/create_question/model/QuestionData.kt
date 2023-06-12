package com.example.demoappcompose.ui.create_question.model

data class QuestionData(
    var isSelected: Boolean = false,
    val question: String?,
    val answers: List<String>?
)
