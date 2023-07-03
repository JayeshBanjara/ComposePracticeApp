package com.example.demoappcompose.data.responses.purchase_book


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BooksData(
    @Json(name = "book_data")
    val bookList: List<Book>
)