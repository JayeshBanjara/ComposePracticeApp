package com.example.demoappcompose.data.responses.dashboard_response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MediumData(
    @Json(name = "id")
    val id: Int,
    @Json(name = "medium_name")
    val mediumName: String
)