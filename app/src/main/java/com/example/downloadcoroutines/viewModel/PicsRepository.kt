package com.example.downloadcoroutines.viewModel

import android.util.Log
import androidx.room.withTransaction
import com.example.downloadcoroutines.apiservices.ApiInterface
import com.example.downloadcoroutines.data.PicsDatabase
import com.example.downloadcoroutines.modelClasses.PicsModel
import com.example.downloadcoroutines.utils.networkBoundResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class PicsRepository @Inject constructor(
    val apiInterface: ApiInterface,
    val picsDatabase: PicsDatabase
) {
    private val picsDAO = picsDatabase.picsDao()


    fun getPics(page: Int, limit: Int) = networkBoundResource(
        query = {
            picsDAO.getPics()
        },
        fetch = {
            delay(2000)
            apiInterface.getPics(page, limit)
        },
        saveFetchResult = { pics ->
            picsDatabase.withTransaction {
                picsDAO.deleteAllPics()
                picsDAO.insetPics(pics as List<PicsModel>)
            }
        }
    )

    suspend fun getAltPics(page: Int, limit: Int): Flow<Response<ArrayList<PicsModel>>> {
        return flow {
            emit(apiInterface.getAltPics(page, limit))
        }.catch { exception ->
            Log.e("exception->", exception.localizedMessage)
            throw exception
        }.flowOn(Dispatchers.IO)
    }
}