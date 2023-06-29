package com.example.demoappcompose.data.responses.my_subscription


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SubscriptionData(
    @Json(name = "subscription_data")
    val subscriptionList: List<Subscription>
)