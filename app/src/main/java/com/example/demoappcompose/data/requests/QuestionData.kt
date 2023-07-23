package com.example.demoappcompose.data.requests


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class QuestionData(
    @Json(name = "options")
    val options: String?,
    @Json(name = "qId")
    val qId: Int,
    @Json(name = "question")
    val question: String
)