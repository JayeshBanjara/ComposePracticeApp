package com.example.demoappcompose.data.requests


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LogoutRequest(
    @Json(name = "device_type")
    val deviceType: Int,
    @Json(name = "user_id")
    val userId: String,
    @Json(name = "login_log_id")
    val loginLogId: String
)