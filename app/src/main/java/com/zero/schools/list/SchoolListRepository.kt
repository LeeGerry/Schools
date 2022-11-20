package com.zero.schools.list

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SchoolListRepository @Inject constructor(
    private val schoolListService: SchoolListService,
    private val mapper: SchoolListMapper

) {
    suspend fun getSchools(): Flow<Result<List<SchoolItem>>> {
        return schoolListService.getAllSchools().map {
            if (it.isSuccess) Result.success(mapper(it.getOrNull()!!))
            else Result.failure(it.exceptionOrNull()!!)
        }
    }
}