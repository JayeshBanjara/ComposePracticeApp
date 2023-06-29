package com.example.demoappcompose.data.responses.dashboard_response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DashboardResponse(
    @Json(name = "data")
    val dashboardData: DashboardData,
    @Json(name = "message")
    val message: String,
    @Json(name = "status_code")
    val statusCode: Int,
    @Json(name = "status_message")
    val statusMessage: String
)