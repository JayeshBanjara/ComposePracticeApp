package com.example.demoappcompose.data.responses.login_response


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
    @Json(name = "large_image_url")
    val largeImageUrl: String,
    @Json(name = "login_log_id")
    val loginLogId: Long,
    @Json(name = "mobile_no")
    val mobileNo: String,
    @Json(name = "password")
    val password: String,
    @Json(name = "role_id")
    val roleId: Int,
    @Json(name = "small_image_url")
    val smallImageUrl: String,
    @Json(name = "status")
    val status: String,
    @Json(name = "updated_at")
    val updatedAt: String,
    @Json(name = "user_id")
    val userId: Long
)