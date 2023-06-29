package com.example.demoappcompose.data.responses.my_subscription


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Subscription(
    @Json(name = "amount")
    val amount: Int,
    @Json(name = "class_id")
    val classId: Int,
    @Json(name = "class_name")
    val className: String,
    @Json(name = "medium_name")
    val mediumName: String,
    @Json(name = "medium_type")
    val mediumType: Int,
    @Json(name = "payment_status")
    val paymentStatus: Int,
    @Json(name = "stream_name")
    val streamName: String,
    @Json(name = "stream_type")
    val streamType: Int,
    @Json(name = "sub_id")
    val subId: Int,
    @Json(name = "subject_name")
    val subjectName: String,
    @Json(name = "subscription_end")
    val subscriptionEnd: String,
    @Json(name = "subscription_start")
    val subscriptionStart: String,
    @Json(name = "user_id")
    val userId: Int
)