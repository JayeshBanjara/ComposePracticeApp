package com.example.demoappcompose.data.responses.my_subscription


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SubscriptionListResponse(
    @Json(name = "data")
    val subscriptionData: SubscriptionData,
    @Json(name = "message")
    val message: String,
    @Json(name = "status_code")
    val statusCode: Int,
    @Json(name = "status_message")
    val statusMessage: String
)