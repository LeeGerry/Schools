package com.zero.schools.details

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
class SchoolDetailViewModelShould : BaseUnitTest() {
    private val repository: SchoolDetailRepository = mock()
    private val schoolDetails: SchoolDetail = mock()
    private val expected = Result.success(schoolDetails)
    private val exception = RuntimeException("Something went wrong")
    private val id = "21K728"

    @Test
    fun `get school details from repository`() = runTest {
        val viewModel = mockSuccessfulCase()
        viewModel.getSchoolDetail(id)
        verify(repository, times(1)).getSchoolDetail(id)
    }

    @Test
    fun `emits details from repository`() = runTest {
        val viewModel = mockSuccessfulCase()
        viewModel.getSchoolDetail(id)
        assertEquals(expected, viewModel.schoolDetail.getValueForTest())
    }

    @Test
    fun `emits error when receive error from repository`() = runTest {
        val viewMode = mockFailureCase()
        viewMode.getSchoolDetail(id)
        assertEquals(exception, viewMode.schoolDetail.getValueForTest()!!.exceptionOrNull())
    }

    @Test
    fun `show spinner while loading`() = runTest {
        val viewModel = mockSuccessfulCase()
        viewModel.loader.captureValues {
            viewModel.getSchoolDetail(id)
            viewModel.schoolDetail.getValueForTest()
            assertEquals(true, values[0])
        }
    }

    @Test
    fun `close loader after school details is loaded`() = runTest {
        val viewModel = mockSuccessfulCase()
        viewModel.loader.captureValues {
            viewModel.getSchoolDetail(id)
            viewModel.schoolDetail.getValueForTest()
            assertEquals(false, values.last())
        }
    }

    @Test
    fun `close loader after error`() = runTest {
        val viewModel = mockFailureCase()
        viewModel.loader.captureValues {
            viewModel.getSchoolDetail(id)
            viewModel.schoolDetail.getValueForTest()
            assertEquals(false, values.last())
        }
    }

    private fun mockSuccessfulCase(): SchoolDetailViewModel {
        runTest {
            whenever(repository.getSchoolDetail(id)).thenReturn(
                flow {
                    emit(expected)
                }
            )
        }
        return SchoolDetailViewModel(repository)
    }

    private fun mockFailureCase(): SchoolDetailViewModel {
        runTest {
            whenever(repository.getSchoolDetail(id)).thenReturn(
                flow {
                    emit(Result.failure(exception))
                }
            )
        }
        return SchoolDetailViewModel(repository)
    }
}