package com.zero.schools.details

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SchoolDetailRepository @Inject constructor(
    private val service: SchoolDetailService,
    private val mapper: SchoolDetailMapper
) {
    suspend fun getSchoolDetail(id: String): Flow<Result<SchoolDetail>> =
        service.getDetails(id).map {
            if (it.isSuccess) Result.success(mapper(it.getOrNull()!!))
            else Result.failure(it.exceptionOrNull()!!)
        }
}