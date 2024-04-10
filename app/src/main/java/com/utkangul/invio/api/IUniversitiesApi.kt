package com.utkangul.invio.api

import com.utkangul.invio.model.UniversityData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface IUniversitiesApi {
    @GET("invio-com/{challenge}/{path}/page-{page}.json")
    fun getUniversities(
        @Path("challenge") challenge: String,
        @Path("path") path: String,
        @Path("page") page: Int
    ): Call<UniversityData>
}