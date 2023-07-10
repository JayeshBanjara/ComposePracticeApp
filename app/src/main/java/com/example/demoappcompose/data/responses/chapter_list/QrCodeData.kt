package com.example.demoappcompose.data.responses.chapter_list


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class QrCodeData(
    @Json(name = "label")
    val label: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "value")
    val value: String
)