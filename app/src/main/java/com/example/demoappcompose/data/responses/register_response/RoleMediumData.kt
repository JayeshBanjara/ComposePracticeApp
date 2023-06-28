package com.example.demoappcompose.data.responses.register_response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RoleMediumData(
    @Json(name = "medium_data")
    val mediumData: List<MediumData>,
    @Json(name = "role_data")
    val roleData: List<RoleData>
)