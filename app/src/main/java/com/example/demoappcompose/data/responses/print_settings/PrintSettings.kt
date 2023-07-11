package com.example.demoappcompose.data.responses.print_settings


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PrintSettings(
    @Json(name = "config_type")
    val configType: Int,
    @Json(name = "default_value")
    val defaultValue: String,
    @Json(name = "desc")
    val desc: String,
    @Json(name = "display_type")
    val displayType: Int,
    @Json(name = "language_input")
    val languageInput: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "order_by")
    val orderBy: Int,
    @Json(name = "select_type")
    val selectType: Int,
    @Json(name = "settings_language")
    val settingsLanguage: List<Any>,
    @Json(name = "source")
    val source: Int,
    @Json(name = "source_value")
    val sourceValue: String,
    @Json(name = "status")
    val status: Int,
    @Json(name = "sub_config_type")
    val subConfigType: Int,
    @Json(name = "value")
    val value: String
)