package com.example.demoappcompose.data.requests


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PaymentApproveRejectRequest(
    @Json(name = "device_type")
    val deviceType: Int,
    @Json(name = "payment_status")
    val paymentStatus: Int,
    @Json(name = "temp_sub_id")
    val tempSubId: String,
    @Json(name = "user_id")
    val userId: String
)