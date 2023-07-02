package com.example.demoappcompose.data.responses.questions


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HeadingListData(
    @Json(name = "subject_heading_data")
    val headingList: List<HeadingData>
)