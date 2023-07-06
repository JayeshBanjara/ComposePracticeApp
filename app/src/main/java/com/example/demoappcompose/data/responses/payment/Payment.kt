package com.example.demoappcompose.data.responses.payment


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Payment(
    @Json(name = "amount") val amount: Int,
    @Json(name = "class_id") val classId: Int,
    @Json(name = "class_name") val className: String,
    @Json(name = "medium_name") val mediumName: String,
    @Json(name = "medium_type") val mediumType: Int,
    @Json(name = "payment_status") val paymentStatus: Int,
    @Json(name = "payment_status_name") var paymentStatusName: String,
    @Json(name = "stream_type") val streamType: Int,
    @Json(name = "stream_type_name") val streamTypeName: String,
    @Json(name = "subject_id") val subjectId: Int,
    @Json(name = "subject_name") val subjectName: String,
    @Json(name = "temp_sub_id") val tempSubId: Int,
    @Json(name = "user_id") val userId: Int
)