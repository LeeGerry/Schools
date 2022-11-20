package com.zero.schools.network

import com.zero.schools.network.model.SchoolDetailsRaw
import com.zero.schools.network.model.SchoolRaw
import retrofit2.http.GET
import retrofit2.http.Query

interface SchoolApi {
    @GET("s3k6-pzi2.json")
    suspend fun getAllSchools(): List<SchoolRaw>

    @GET("f9bf-2cp4.json")
    suspend fun getSchoolDetails(@Query("DBN") dbn: String): List<SchoolDetailsRaw>
}