package com.example.demoappcompose.data.responses.question_list


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class QuestionTypeData(
    @Json(name = "chapter_type")
    val questionType: String,
    @Json(name = "chapter_type_id")
    val questionTypeId: Int
)