package com.example.demoappcompose.ui.create_question.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class QuestionsArr(
    @Json(name = "options")
    val options: String?,
    @Json(name = "qId")
    val qId: Int,
    @Json(name = "question")
    val question: String
)