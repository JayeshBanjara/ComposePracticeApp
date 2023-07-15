package com.example.demoappcompose.data.requests


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class QuestionListRequest(
    @Json(name = "device_type") val deviceType: Int,
    @Json(name = "user_id") val userId: String,
    @Json(name = "class_id") val classId: String,
    @Json(name = "subject_id") val subjectId: String,
    @Json(name = "chapter_id") val chapterId: String,
    @Json(name = "chapter_type_id") val questionTypeId: String
)