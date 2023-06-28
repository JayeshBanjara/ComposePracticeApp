package com.example.demoappcompose.data.responses.login_response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserMediumData(
    @Json(name = "medium_id")
    val mediumId: Int,
    @Json(name = "medium_name")
    val mediumName: String,
    @Json(name = "um_id")
    val umId: Int,
    @Json(name = "user_id")
    val userId: Int
)