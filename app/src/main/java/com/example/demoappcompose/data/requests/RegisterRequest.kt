package com.example.demoappcompose.data.requests


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegisterRequest(
    @Json(name = "city")
    val city: String,
    @Json(name = "device_type")
    val deviceType: Int,
    @Json(name = "email")
    val email: String,
    @Json(name = "full_name")
    val fullName: String,
    @Json(name = "institute_logo")
    val instituteLogo: String,
    @Json(name = "institute_name")
    val instituteName: String,
    @Json(name = "medium_id")
    val mediumId: List<String>,
    @Json(name = "mobile_no")
    val mobileNo: String,
    @Json(name = "password")
    val password: String,
    @Json(name = "role_id")
    val roleId: String
)