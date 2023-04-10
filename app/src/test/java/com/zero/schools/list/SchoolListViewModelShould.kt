package com.zero.schools.list

import com.zero.schools.utils.BaseUnitTest
import com.zero.schools.utils.captureValues
import com.zero.schools.utils.getValueForTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class SchoolListViewModelShould : BaseUnitTest() {
    private val repository: SchoolListRepository = mock()
    private val schoolList = mock<List<SchoolItem>>()
    private val expected = Result.success(schoolList)
    private val exception = RuntimeException("Something went wrong")

    @Test
    fun `get school list from repository`() = runTest {
        val viewModel = mockSuccessfulCase()
        viewModel.schoolList.getValueForTest()
        verify(repository, times(2)).getSchools()
    }

    @Test
    fun `emits school list from repository`() = runTest {
        val viewMode = mockSuccessfulCase()
        assertEquals(expected, viewMode.schoolList.getValueForTest())
    }

    @Test
    fun `emits error when receive error from repository`() = runTest {
        val viewModel = mockFailureCase()
        assertEquals(exception, viewModel.schoolList.getValueForTest()!!.exceptionOrNull())
    }

    @Test
    fun `show spinner while loading`() = runTest {
        val viewModel = mockSuccessfulCase()
        viewModel.loader.captureValues {
            viewModel.schoolList.getValueForTest()
            assertEquals(true, values[0])
        }
    }

    @Test
    fun `close loader after school list is loaded`() = runTest {
        val viewModel = mockSuccessfulCase()
        viewModel.loader.captureValues {
            viewModel.schoolList.getValueForTest()
            assertEquals(false, values.last())
        }
    }

    @Test
    fun `close loader after error`() = runTest {
        val viewModel = mockFailureCase()
        viewModel.loader.captureValues {
            viewModel.schoolList.getValueForTest()
            assertEquals(false, values.last())
        }
    }

    private fun mockSuccessfulCase(): SchoolListViewModel {
        runTest {
            whenever(repository.getSchools()).thenReturn(
                flow { emit(expected) }
            )
        }
        return SchoolListViewModel(repository)
    }

    private fun mockFailureCase(): SchoolListViewModel {
        runTest {
            whenever(repository.getSchools()).thenReturn(
                flow { emit(Result.failure(exception)) }
            )
        }
        return SchoolListViewModel(repository)
    }
}