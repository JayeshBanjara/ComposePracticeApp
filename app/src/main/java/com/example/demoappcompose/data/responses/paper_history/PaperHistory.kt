package com.example.demoappcompose.data.responses.paper_history


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PaperHistory(
    @Json(name = "class_id")
    val classId: Int,
    @Json(name = "class_name")
    val className: String,
    @Json(name = "created_at")
    val createdAt: String,
    @Json(name = "file_name")
    val fileName: String,
    @Json(name = "file_url")
    val fileUrl: String,
    @Json(name = "generation_name")
    val generationName: String,
    @Json(name = "generation_type")
    val generationType: Int,
    @Json(name = "institute_name")
    val instituteName: String,
    @Json(name = "medium_name")
    val mediumName: String,
    @Json(name = "medium_type")
    val mediumType: Int,
    @Json(name = "p_id")
    val pId: Int,
    @Json(name = "stream_name")
    val streamName: String,
    @Json(name = "stream_type")
    val streamType: Int,
    @Json(name = "subject_name")
    val subjectName: String,
    @Json(name = "user_id")
    val userId: Int
)