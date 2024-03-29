package com.example.demoappcompose.data.responses.subjects


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SubjectListResponse(
    @Json(name = "data")
    val subjectData: SubjectData,
    @Json(name = "message")
    val message: String,
    @Json(name = "status_code")
    val statusCode: Int,
    @Json(name = "status_message")
    val statusMessage: String
)