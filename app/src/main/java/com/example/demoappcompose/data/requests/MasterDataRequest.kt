package com.example.demoappcompose.data.requests


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MasterDataRequest(
    @Json(name = "device_type")
    val deviceType: Int,
    @Json(name = "data-request")
    val dataRequest: List<String>
)