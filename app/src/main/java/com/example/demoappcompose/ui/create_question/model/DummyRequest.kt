package com.example.demoappcompose.ui.create_question.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DummyRequest(
    @Json(name = "heading")
    val heading: String,
    @Json(name = "isSectionName")
    val isSectionName: Boolean,
    @Json(name = "marks")
    val marks: Int,
    @Json(name = "questions_arr")
    val questionsArr: List<QuestionsArr>,
    @Json(name = "sectionName")
    val sectionName: String
)