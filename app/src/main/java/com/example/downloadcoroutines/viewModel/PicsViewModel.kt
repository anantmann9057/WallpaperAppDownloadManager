package com.example.downloadcoroutines.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.downloadcoroutines.apiservices.ApiInterface
import com.example.downloadcoroutines.modelClasses.PicsModel
import com.example.downloadcoroutines.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PicsViewModel @Inject constructor(val apiInterface: ApiInterface) : ViewModel() {

    val picsResponse by lazy { MutableLiveData<Resource<ArrayList<PicsModel>>>() }
    fun getPics(page: Int, limit: Int) {
        viewModelScope.launch {
            picsResponse.postValue(Resource.loading(null))
            apiInterface.getPics(page, limit)
                .let {
                    if (it.isSuccessful) {
                        picsResponse.postValue(Resource.success(it.body()))
                    } else {
                        picsResponse.postValue(Resource.error(it.errorBody().toString(), it.body()))
                    }
                }
        }
    }


}