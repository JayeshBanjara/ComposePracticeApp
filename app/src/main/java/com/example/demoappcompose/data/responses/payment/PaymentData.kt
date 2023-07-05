package com.example.demoappcompose.data.responses.payment


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PaymentData(
    @Json(name = "temp_subscription_data")
    val paymentList: List<Payment>
)