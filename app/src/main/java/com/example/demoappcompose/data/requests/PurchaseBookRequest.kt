package com.example.demoappcompose.data.requests


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PurchaseBookRequest(
    @Json(name = "book_id")
    val bookId: String,
    @Json(name = "city")
    val city: String,
    @Json(name = "device_type")
    val deviceType: Int,
    @Json(name = "email")
    val email: String,
    @Json(name = "first_name")
    val firstName: String,
    @Json(name = "full_address")
    val fullAddress: String,
    @Json(name = "last_name")
    val lastName: String,
    @Json(name = "mobile_no")
    val mobileNo: String,
    @Json(name = "qty")
    val qty: Int,
    @Json(name = "state")
    val state: String,
    @Json(name = "user_id")
    val userId: String
)