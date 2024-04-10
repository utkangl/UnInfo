package com.utkangul.invio.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UniversityData(
    @Json(name = "currentPage") val currentPage: Int,
    @Json(name = "totalPage") val totalPage: Int,
    @Json(name = "total") val total: Int,
    @Json(name = "itemPerPage") val itemPerPage: Int,
    @Json(name = "pageSize") val pageSize: Int,
    @Json(name = "data") val data: MutableList<University>
)

@JsonClass(generateAdapter = true)
data class University(
    @Json(name = "id") val id: Int,
    @Json(name = "province") val province: String,
    @Json(name = "universities") val universities: List<UniversityInfo>
)

@JsonClass(generateAdapter = true)
data class UniversityInfo(
    @Json(name = "name") val name: String?,
    @Json(name = "phone") val phone: String?,
    @Json(name = "fax") val fax: String?,
    @Json(name = "website") val website: String?,
    @Json(name = "email") val email: String?,
    @Json(name = "address") val address: String?,
    @Json(name = "rector") val rector: String?
)
