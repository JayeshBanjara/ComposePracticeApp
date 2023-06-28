package com.example.demoappcompose.data.responses.register_response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegisterData(
    @Json(name = "medium_data")
    val mediumData: List<MediumDataX>,
    @Json(name = "user_data")
    val userData: UserData,
    @Json(name = "user_id")
    val userId: Int
)