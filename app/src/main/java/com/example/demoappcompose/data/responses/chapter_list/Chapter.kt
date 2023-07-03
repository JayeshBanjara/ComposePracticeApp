package com.example.demoappcompose.data.responses.chapter_list


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Chapter(
    @Json(name = "amount")
    val amount: Int,
    @Json(name = "chapter_name")
    val chapterName: String,
    @Json(name = "chapter_title")
    val chapterTitle: String,
    @Json(name = "chapter_type")
    val chapterType: String,
    @Json(name = "chp_id")
    val chpId: Int,
    @Json(name = "class_id")
    val classId: Int,
    @Json(name = "class_name")
    val className: String,
    @Json(name = "is_free")
    val isFree: Int,
    @Json(name = "subject_id")
    val subjectId: Int,
    @Json(name = "subject_name")
    val subjectName: String,
    @Json(name = "subscription_month")
    val subscriptionMonth: Int
)