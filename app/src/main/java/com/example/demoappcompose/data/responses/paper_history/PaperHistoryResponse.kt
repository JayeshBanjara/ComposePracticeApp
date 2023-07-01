package com.example.demoappcompose.data.responses.paper_history


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PaperHistoryResponse(
    @Json(name = "data")
    val paperHistoryData: PaperHistoryData,
    @Json(name = "message")
    val message: String,
    @Json(name = "status_code")
    val statusCode: Int,
    @Json(name = "status_message")
    val statusMessage: String
)