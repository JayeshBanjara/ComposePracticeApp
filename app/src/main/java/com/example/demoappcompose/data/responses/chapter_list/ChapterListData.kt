package com.example.demoappcompose.data.responses.chapter_list


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChapterListData(
    @Json(name = "chapter_data")
    val chapterList: List<Chapter>
)