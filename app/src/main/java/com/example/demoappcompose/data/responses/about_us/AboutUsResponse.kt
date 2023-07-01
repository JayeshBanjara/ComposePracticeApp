package com.example.demoappcompose.data.responses.about_us


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AboutUsResponse(
    @Json(name = "data")
    val aboutUsData: AboutUsData,
    @Json(name = "message")
    val message: String,
    @Json(name = "status_code")
    val statusCode: Int,
    @Json(name = "status_message")
    val statusMessage: String
)