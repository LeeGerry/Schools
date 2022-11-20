package com.zero.schools.details

import com.zero.schools.network.SchoolApi
import com.zero.schools.network.model.SchoolDetailsRaw
import com.zero.schools.utils.BaseUnitTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class SchoolDetailServiceShould : BaseUnitTest() {
    private lateinit var service: SchoolDetailService
    private val api: SchoolApi = mock()
    private val details: SchoolDetailsRaw = mock()
    private val id = "21K728"

    @Test
    fun `fetch school detail from api`() = runTest {
        service = SchoolDetailService(api)
        service.getDetails(id).collect()
        verify(api, times(1)).getSchoolDetails(id)
    }

    @Test
    fun `convert values to flow result and emits them`() = runTest {
        mockSuccessfulCase()
        assertEquals(Result.success(details), service.getDetails(id).first())
    }

    @Test
    fun `emits error result when network fails`() = runTest {
        mockFailureCase()
        assertEquals(
            "Service exception!",
            service.getDetails(id).first().exceptionOrNull()?.message
        )
    }

    private suspend fun mockSuccessfulCase() {
        whenever(api.getSchoolDetails(id)).thenReturn(listOf(details))
        service = SchoolDetailService(api)
    }

    private suspend fun mockFailureCase() {
        whenever(api.getSchoolDetails(id)).thenThrow(RuntimeException("Backend error"))
        service = SchoolDetailService(api)
    }
}