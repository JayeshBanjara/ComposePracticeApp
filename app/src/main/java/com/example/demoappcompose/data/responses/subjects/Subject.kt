package com.example.demoappcompose.data.responses.subjects


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Subject(
    @Json(name = "c_id")
    val cId: Int,
    @Json(name = "class_id")
    val classId: Int,
    @Json(name = "class_name")
    val className: String,
    @Json(name = "is_stream")
    val isStream: Int,
    @Json(name = "is_stream_type")
    val isStreamType: String,
    @Json(name = "medium_name")
    val mediumName: String,
    @Json(name = "medium_type")
    val mediumType: Int,
    @Json(name = "s_id")
    val sId: Int,
    @Json(name = "stream_type")
    val streamType: Int,
    @Json(name = "stream_type_name")
    val streamTypeName: String,
    @Json(name = "subject_book")
    val subjectBook: String,
    @Json(name = "subject_name")
    val subjectName: String
)