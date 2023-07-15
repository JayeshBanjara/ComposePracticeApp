package com.example.demoappcompose.data.requests


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GeneratePaymentRequest(
    @Json(name = "amount")
    val amount: String,
    @Json(name = "class_id")
    val classId: String,
    @Json(name = "device_type")
    val deviceType: Int,
    @Json(name = "subject_id")
    val subjectId: String,
    @Json(name = "user_id")
    val userId: String
)