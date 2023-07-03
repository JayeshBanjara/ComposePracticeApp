package com.example.demoappcompose.data.responses.subjects


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SubjectData(
    @Json(name = "subject_data")
    val subjectList: List<Subject>
)