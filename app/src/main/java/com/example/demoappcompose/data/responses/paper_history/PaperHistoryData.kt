package com.example.demoappcompose.data.responses.paper_history


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PaperHistoryData(
    @Json(name = "paper_history_data")
    val paperHistoryList: List<PaperHistory>
)