package com.zero.schools.list

import com.zero.schools.network.model.SchoolRaw
import com.zero.schools.utils.BaseUnitTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class SchoolListRepositoryShould : BaseUnitTest() {
    private val service: SchoolListService = mock()
    private val mapper: SchoolListMapper = mock()
    private val schoolListRaw = mock<List<SchoolRaw>>()
    private val schoolList = mock<List<SchoolItem>>()
    private val exception = RuntimeException("Something went wrong")

    @Test
    fun `get schools from service`() = runTest {
        val repository = mockSuccessfulCase()
        repository.getSchools()
        verify(service, times(1)).getAllSchools()
    }

    @Test
    fun `emit mapped school list from service`() = runTest {
        val repository = mockSuccessfulCase()
        assertEquals(schoolList, repository.getSchools().first().getOrNull())
    }

    @Test
    fun `error happens when fetch from service`() = runTest {
        val repository = mockFailureCase()
        assertEquals(exception, repository.getSchools().first().exceptionOrNull())
    }

    @Test
    fun `delegate business logic to mapper`() = runTest {
        val repository = mockSuccessfulCase()
        repository.getSchools().first()
        verify(mapper, times(1)).invoke(schoolListRaw)
    }

    private suspend fun mockFailureCase(): SchoolListRepository {
        whenever(service.getAllSchools()).thenReturn(
            flow {
                emit(Result.failure(exception))
            }
        )
        return SchoolListRepository(service, mapper)
    }

    private suspend fun mockSuccessfulCase(): SchoolListRepository {
        whenever(service.getAllSchools()).thenReturn(
            flow {
                emit(Result.success(schoolListRaw))
            }
        )
        whenever(mapper.invoke(schoolListRaw)).thenReturn(schoolList)
        return SchoolListRepository(service, mapper)
    }
}