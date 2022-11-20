package com.zero.schools.list

data class SchoolItem(
    val id: String,
    val name: String,
    val overview: String? = null,
    val website: String? = null,
    val phone: String? = null,
    val email: String? = null,
    val location: String? = null
)