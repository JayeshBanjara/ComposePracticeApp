package com.example.demoappcompose.data.responses.purchase_book


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Book(
    @Json(name = "b_id")
    val bId: Int,
    @Json(name = "book_name")
    val bookName: String,
    @Json(name = "courier_charge")
    val courierCharge: Double,
    @Json(name = "discount")
    val discount: Double,
    @Json(name = "final_amount")
    val finalAmount: Double,
    @Json(name = "price")
    val price: Double,
    @Json(name = "status")
    val status: Int
)