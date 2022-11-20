package com.zero.schools.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zero.schools.utils.EspressoIdlingResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SchoolDetailViewModel @Inject constructor(
    private val schoolDetailRepository: SchoolDetailRepository
) : ViewModel() {
    val loader = MutableLiveData<Boolean>()
    val schoolDetail: MutableLiveData<Result<SchoolDetail>> = MutableLiveData()

    fun getSchoolDetail(id: String) {
        viewModelScope.launch {
            EspressoIdlingResource.increment()
            loader.postValue(true)
            schoolDetailRepository.getSchoolDetail(id)
                .onEach {
                    loader.postValue(false)
                    EspressoIdlingResource.decrement()
                }
                .collect { schoolDetail.postValue(it) }
        }
    }
}