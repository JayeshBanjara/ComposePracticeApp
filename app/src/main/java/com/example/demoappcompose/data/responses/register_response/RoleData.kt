package com.example.demoappcompose.data.responses.register_response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RoleData(
    @Json(name = "id")
    val id: Int,
    @Json(name = "role_name")
    val roleName: String
)