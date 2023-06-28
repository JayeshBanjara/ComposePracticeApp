package com.example.demoappcompose.data.responses.register_response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetRoleMediumDataResponse(
    @Json(name = "data")
    val roleMediumData: RoleMediumData,
    @Json(name = "message")
    val message: String,
    @Json(name = "status_code")
    val statusCode: Int,
    @Json(name = "status_message")
    val statusMessage: String
)