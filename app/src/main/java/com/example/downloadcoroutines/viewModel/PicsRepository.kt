package com.example.downloadcoroutines.viewModel

import android.util.Log
import com.example.downloadcoroutines.apiservices.ApiInterface
import com.example.downloadcoroutines.modelClasses.PicsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class PicsRepository @Inject constructor(val apiInterface: ApiInterface) {

    suspend fun getPics(page: Int, limit: Int): Flow<Response<ArrayList<PicsModel>>> {
        return flow<Response<ArrayList<PicsModel>>> {
            emit(apiInterface.getPics(page, limit))
        }.catch { exception ->
            Log.e("exception->", exception.localizedMessage)
            throw exception
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getAltPics(page: Int, limit: Int): Flow<Response<ArrayList<PicsModel>>> {
        return flow {
            emit(apiInterface.getAltPics(page, limit))
        }.catch { exception ->
            Log.e("exception->", exception.localizedMessage)
            throw exception
        }.flowOn(Dispatchers.IO)
    }
}