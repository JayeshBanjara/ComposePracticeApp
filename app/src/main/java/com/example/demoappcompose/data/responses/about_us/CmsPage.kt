package com.example.demoappcompose.data.responses.about_us


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CmsPage(
    @Json(name = "id")
    val id: Int,
    @Json(name = "page_code")
    val pageCode: String,
    @Json(name = "page_content")
    val pageContent: String,
    @Json(name = "page_name")
    val pageName: String,
    @Json(name = "page_slug")
    val pageSlug: String
)