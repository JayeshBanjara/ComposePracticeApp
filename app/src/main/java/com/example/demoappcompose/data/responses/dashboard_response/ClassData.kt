package com.example.demoappcompose.data.responses.dashboard_response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ClassData(
    @Json(name = "c_id")
    val cId: Int,
    @Json(name = "class_name")
    val className: String,
    @Json(name = "is_stream")
    val isStream: String,
    @Json(name = "medium_type")
    val mediumType: String,
    @Json(name = "stream_type")
    val streamType: String
)