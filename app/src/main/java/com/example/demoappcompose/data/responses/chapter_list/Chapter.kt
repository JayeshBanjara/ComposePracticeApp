package com.example.demoappcompose.data.responses.chapter_list


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Chapter(
    @Json(name = "chapter_name") val chapterName: String,
    @Json(name = "chapter_title") val chapterTitle: String,
    @Json(name = "chp_id") val chpId: Int,
    @Json(name = "class_id") val classId: Int,
    @Json(name = "class_name") val className: String,
    @Json(name = "qr_code_data") val qrCodeData: List<QrCodeData>,
    @Json(name = "subject_id") val subjectId: Int,
    @Json(name = "subject_name") val subjectName: String
)