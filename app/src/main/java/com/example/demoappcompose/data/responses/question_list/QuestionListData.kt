package com.example.demoappcompose.data.responses.question_list


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class QuestionListData(
    @Json(name = "chapter_type_data")
    val questionTypeList: List<QuestionTypeData>,
    @Json(name = "question_data")
    val questionList: MutableList<QuestionData>
)