package com.zero.schools.list

import com.zero.schools.network.SchoolApi
import com.zero.schools.network.model.SchoolRaw
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
class SchoolListServiceShould : BaseUnitTest() {
    private lateinit var service: SchoolListService
    private val api: SchoolApi = mock()
    private val schoolList: List<SchoolRaw> = mock()

    @Test
    fun `fetch school list from api`() = runTest {
        service = SchoolListService(api)
        service.getAllSchools().collect()
        verify(api, times(1)).getAllSchools()
    }

    @Test
    fun `convert values to flow result and emits them`() = runTest {
        whenever(api.getAllSchools()).thenReturn(schoolList)
        service = SchoolListService(api)
        assertEquals(Result.success(schoolList), service.getAllSchools().first())
    }

    @Test
    fun `emits error result when network fails`() = runTest {
        whenever(api.getAllSchools()).thenThrow(RuntimeException("Backend exception"))
        service = SchoolListService(api)
        assertEquals(
            "Service exception!",
            service.getAllSchools().first().exceptionOrNull()?.message
        )
    }
}