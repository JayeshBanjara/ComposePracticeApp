package com.example.demoappcompose.data.requests


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ConfigRequest(
    @Json(name = "device_type")
    val deviceType: Int,
    @Json(name = "config_type")
    val configType: String
)