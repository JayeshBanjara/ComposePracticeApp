package com.example.demoappcompose.data.responses.about_us


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AboutUsData(
    @Json(name = "cmspage")
    val cmspage: List<CmsPage>
)