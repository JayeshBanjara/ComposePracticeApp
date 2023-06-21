package com.example.demoappcompose.data.requests


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginRequest(
    @Json(name = "device_model")
    val deviceModel: String,
    @Json(name = "device_name")
    val deviceName: String,
    @Json(name = "device_no")
    val deviceNo: String,
    @Json(name = "device_platform")
    val devicePlatform: String,
    @Json(name = "device_type")
    val deviceType: Int,
    @Json(name = "device_uuid")
    val deviceUuid: String,
    @Json(name = "device_version")
    val deviceVersion: String,
    @Json(name = "mobile_no")
    val mobileNo: String,
    @Json(name = "password")
    val password: String
)