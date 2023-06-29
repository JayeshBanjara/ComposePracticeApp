package com.example.demoappcompose.data.responses.dashboard_response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DashboardData(
    @Json(name = "appplication_visitor_count")
    val applicationVisitorCount: Int,
    @Json(name = "class_data")
    val classList: List<ClassData>,
    @Json(name = "medium_data")
    val mediumList: List<MediumData>
)