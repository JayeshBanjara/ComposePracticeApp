package com.example.demoappcompose.data.responses.payment


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PaymentApproveRejectResponse(
    @Json(name = "data")
    val paymentApproveRejectData: PaymentApproveRejectData,
    @Json(name = "message")
    val message: String,
    @Json(name = "status_code")
    val statusCode: Int,
    @Json(name = "status_message")
    val statusMessage: String
)