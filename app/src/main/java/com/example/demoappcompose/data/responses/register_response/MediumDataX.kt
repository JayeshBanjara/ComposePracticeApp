package com.example.demoappcompose.data.responses.register_response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MediumDataX(
    @Json(name = "id")
    val id: String,
    @Json(name = "medium_name")
    val mediumName: String
)