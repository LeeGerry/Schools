package com.zero.schools.list

import com.zero.schools.network.SchoolApi
import com.zero.schools.network.model.SchoolRaw
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SchoolListService @Inject constructor(private val schoolApi: SchoolApi) {
    suspend fun getAllSchools(): Flow<Result<List<SchoolRaw>>> {
        return flow {
            emit(Result.success(schoolApi.getAllSchools()))
        }.catch {
            emit(Result.failure(RuntimeException("Service exception!")))
        }
    }
}