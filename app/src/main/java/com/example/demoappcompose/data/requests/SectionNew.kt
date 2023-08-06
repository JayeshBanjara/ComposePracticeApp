package com.example.demoappcompose.data.requests

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SectionNew(
    @Json(name = "heading")
    val heading: String,
    @Json(name = "heading_name")
    val headingKey: Int,
    @Json(name = "isSectionName")
    val isSectionName: Boolean,
    @Json(name = "marks")
    val marks: Int,
    @Json(name = "questionsArr")
    val questionsArr: List<QuestionData>,
    @Json(name = "section_name")
    val sectionName: String
)