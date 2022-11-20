package com.zero.schools.details

import com.zero.schools.network.model.SchoolDetailsRaw
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
class SchoolDetailRepositoryShould : BaseUnitTest() {
    private val service: SchoolDetailService = mock()
    private val mapper: SchoolDetailMapper = mock()
    private val schoolDetailsRaw: SchoolDetailsRaw = mock()
    private val schoolDetails: SchoolDetail = mock()
    private val exception = RuntimeException("Something went wrong")
    private val id = "21K728"

    @Test
    fun `get school details from service`() = runTest {
        val repository = mockSuccessfulCase()
        repository.getSchoolDetail(id)
        verify(service, times(1)).getDetails(id)
    }

    @Test
    fun `emit mapped school list from service`() = runTest {
        val repository = mockSuccessfulCase()
        assertEquals(schoolDetails, repository.getSchoolDetail(id).first().getOrNull())
    }

    @Test
    fun `error happens when fetch detail from service`() = runTest {
        val repository = mockFailureCase()
        assertEquals(exception, repository.getSchoolDetail(id).first().exceptionOrNull())
    }

    @Test
    fun `delegate business logic to mapper`() = runTest {
        val repository = mockSuccessfulCase()
        repository.getSchoolDetail(id).first()
        verify(mapper, times(1)).invoke(schoolDetailsRaw)
    }

    private suspend fun mockSuccessfulCase(): SchoolDetailRepository {
        whenever(service.getDetails(id)).thenReturn(
            flow {
                emit(Result.success(schoolDetailsRaw))
            }
        )
        whenever(mapper.invoke(schoolDetailsRaw)).thenReturn(schoolDetails)
        return SchoolDetailRepository(service, mapper)
    }

    private suspend fun mockFailureCase(): SchoolDetailRepository {
        whenever(service.getDetails(id)).thenReturn(
            flow {
                emit(Result.failure(exception))
            }
        )
        return SchoolDetailRepository(service, mapper)
    }
}