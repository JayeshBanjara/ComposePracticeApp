package com.example.demoappcompose.data.responses.profile


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProfileData(
    @Json(name = "user_data")
    val userData: UserData,
    @Json(name = "user_id")
    val userId: String
)