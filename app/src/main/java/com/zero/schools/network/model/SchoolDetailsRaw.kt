package com.zero.schools.network.model

import com.google.gson.annotations.SerializedName

data class SchoolDetailsRaw(
    @SerializedName("dbn") val dbn: String,
    @SerializedName("school_name") val schoolName: String,
    @SerializedName("num_of_sat_test_takers") val numberOfTestTakers: String,
    @SerializedName("sat_critical_reading_avg_score") val readingScore: String,
    @SerializedName("sat_math_avg_score") val mathScore: String,
    @SerializedName("sat_writing_avg_score") val writingScore: String
)