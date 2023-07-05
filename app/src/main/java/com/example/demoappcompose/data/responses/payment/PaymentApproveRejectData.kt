package com.example.demoappcompose.data.responses.payment


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PaymentApproveRejectData(
    @Json(name = "temp_sub_id")
    val tempSubId: String
)