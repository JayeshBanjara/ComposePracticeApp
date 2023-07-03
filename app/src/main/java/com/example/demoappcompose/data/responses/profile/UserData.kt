package com.example.demoappcompose.data.responses.profile


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserData(
    @Json(name = "email")
    val email: String,
    @Json(name = "full_name")
    val fullName: String,
    @Json(name = "institute_logo")
    val instituteLogo: String,
    @Json(name = "large_image_url")
    val largeImageUrl: String,
    @Json(name = "mobile_no")
    val mobileNo: String,
    @Json(name = "small_image_url")
    val smallImageUrl: String,
    @Json(name = "updated_at")
    val updatedAt: String
)