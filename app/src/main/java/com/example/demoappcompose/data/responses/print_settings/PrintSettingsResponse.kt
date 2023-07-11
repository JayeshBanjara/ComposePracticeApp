package com.example.demoappcompose.data.responses.print_settings


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PrintSettingsResponse(
    @Json(name = "data")
    val printSettingsData: List<PrintSettings>,
    @Json(name = "message")
    val message: String,
    @Json(name = "status_code")
    val statusCode: Int,
    @Json(name = "status_message")
    val statusMessage: String
)