package com.zero.schools.network.model

import com.google.gson.annotations.SerializedName

data class SchoolRaw(
    @SerializedName("dbn") val dbn: String,
    @SerializedName("school_name") val schoolName: String,
    @SerializedName("overview_paragraph") val overViewParagraph: String,
    @SerializedName("website") val website: String,
    @SerializedName("phone_number") val phone: String,
    @SerializedName("school_email") val email: String,
    @SerializedName("total_students") val totalStudents: String,
    @SerializedName("city") val city: String,
    @SerializedName("zip") val zip: String,
    @SerializedName("location") val location: String
)
