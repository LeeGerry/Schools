package com.zero.schools.details

import com.zero.schools.network.SchoolApi
import com.zero.schools.network.model.SchoolDetailsRaw
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SchoolDetailService @Inject constructor(private val api: SchoolApi) {
    suspend fun getDetails(dbn: String): Flow<Result<SchoolDetailsRaw>> {
        return flow {
            emit(Result.success(api.getSchoolDetails(dbn).first()))
        }.catch {
            emit(Result.failure(RuntimeException("Service exception!")))
        }
    }
}