package com.zero.schools.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SchoolListViewModel @Inject constructor(
    private val repository: SchoolListRepository
) : ViewModel() {

    val loader = MutableLiveData<Boolean>()

    val schoolList = liveData {
        loader.postValue(true)
        emitSource(
            repository.getSchools()
                .onEach { loader.postValue(false) }
                .asLiveData()
        )
    }
}