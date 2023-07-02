package com.example.demoappcompose.data.requests


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UpdateProfileRequest(
    @Json(name = "device_type")
    val deviceType: Int,
    @Json(name = "email")
    val email: String,
    @Json(name = "full_name")
    val fullName: String,
    @Json(name = "institute_logo")
    val instituteLogo: String?,
    @Json(name = "mobile_no")
    val mobileNo: String,
    @Json(name = "user_id")
    val userId: String
)