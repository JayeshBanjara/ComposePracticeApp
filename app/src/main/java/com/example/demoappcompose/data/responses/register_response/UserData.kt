package com.example.demoappcompose.data.responses.register_response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserData(
    @Json(name = "city")
    val city: String,
    @Json(name = "created_at")
    val createdAt: String,
    @Json(name = "email")
    val email: String,
    @Json(name = "full_name")
    val fullName: String,
    @Json(name = "institute_logo")
    val instituteLogo: String,
    @Json(name = "institute_name")
    val instituteName: String,
    @Json(name = "mobile_no")
    val mobileNo: String,
    @Json(name = "password")
    val password: String,
    @Json(name = "role_id")
    val roleId: String
)