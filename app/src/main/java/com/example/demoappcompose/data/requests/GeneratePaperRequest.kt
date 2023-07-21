package com.example.demoappcompose.data.requests


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GeneratePaperRequest(
    @Json(name = "chapter_number")
    val chapterNumber: String,
    @Json(name = "class_id")
    val classId: String,
    @Json(name = "class_name")
    val className: String,
    @Json(name = "device_type")
    val deviceType: Int,
    @Json(name = "exam_date")
    val examDate: String,
    @Json(name = "exam_marks")
    val examMarks: String,
    @Json(name = "exam_name")
    val examName: String,
    @Json(name = "exam_time")
    val examTime: String,
    @Json(name = "font_size")
    val fontSize: String,
    @Json(name = "generation_type")
    val generationType: String,
    @Json(name = "institute_name")
    val instituteName: String,
    @Json(name = "is_page_footer")
    val isPageFooter: String,
    @Json(name = "is_water_mark")
    val isWaterMark: String,
    @Json(name = "medium_id")
    val mediumId: String,
    @Json(name = "message_for_end_of_paper")
    val messageForEndOfPaper: String,
    @Json(name = "page_border")
    val pageBorder: String,
    @Json(name = "page_footer")
    val pageFooter: String,
    @Json(name = "question_paper")
    val sectionList: List<Section>? = null,
    @Json(name = "subject_id")
    val subjectId: String,
    @Json(name = "subject_name")
    val subjectName: String,
    @Json(name = "user_id")
    val userId: String,
    @Json(name = "water_mark")
    val waterMark: String
)