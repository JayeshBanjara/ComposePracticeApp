package com.example.demoappcompose.data.responses.questions


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class HeadingData(
    @Json(name = "class_id")
    val classId: Int,
    @Json(name = "class_name")
    val className: String,
    @Json(name = "h_id")
    val hId: Int,
    @Json(name = "heading_name")
    val headingName: String,
    @Json(name = "subject_id")
    val subjectId: Int,
    @Json(name = "subject_name")
    val subjectName: String
): Serializable {
    override fun toString(): String {
        return headingName
    }
}