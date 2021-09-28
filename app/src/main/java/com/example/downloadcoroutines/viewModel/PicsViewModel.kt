package com.example.downloadcoroutines.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.downloadcoroutines.modelClasses.PicsModel
import com.nexogic.apiservices.ApiInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PicsViewModel @Inject constructor(val apiInterface: ApiInterface) : ViewModel() {

    val picsResponse by lazy { MutableLiveData<ArrayList<PicsModel>>() }
    fun getPics(page: Int, limit: Int) {
        viewModelScope.launch {
            picsResponse.value = apiInterface.getPics(page, limit)
        }
    }


}