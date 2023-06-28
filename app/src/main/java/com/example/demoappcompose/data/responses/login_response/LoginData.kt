package com.example.demoappcompose.data.responses.login_response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginData(
    @Json(name = "token")
    val token: String,
    @Json(name = "user_data")
    val userData: List<UserData>,
    @Json(name = "user_medium_data")
    val userMediumData: List<UserMediumData>
)