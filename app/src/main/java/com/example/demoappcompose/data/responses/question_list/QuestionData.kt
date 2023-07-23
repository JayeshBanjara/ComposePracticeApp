package com.example.demoappcompose.data.responses.question_list


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class QuestionData(
    @Json(name = "answer")
    val answer: String,
    @Json(name = "chapter_id")
    val chapterId: Int,
    @Json(name = "chapter_type_id")
    val chapterTypeId: Int,
    @Json(name = "class_id")
    val classId: Int,
    @Json(name = "class_name")
    val className: String,
    @Json(name = "from_book")
    val fromBook: Int,
    @Json(name = "options")
    val options: String?,
    @Json(name = "q_id")
    val qId: Int,
    @Json(name = "question")
    val question: String,
    @Json(name = "subject_id")
    val subjectId: Int,
    @Json(name = "subject_name")
    val subjectName: String,
    var isSelected: Boolean = false
)