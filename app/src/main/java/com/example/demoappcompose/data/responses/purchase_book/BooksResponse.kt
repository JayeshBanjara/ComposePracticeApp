package com.example.demoappcompose.data.responses.purchase_book


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BooksResponse(
    @Json(name = "data")
    val booksData: BooksData,
    @Json(name = "message")
    val message: String,
    @Json(name = "status_code")
    val statusCode: Int,
    @Json(name = "status_message")
    val statusMessage: String
)