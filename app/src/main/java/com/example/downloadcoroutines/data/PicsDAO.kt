package com.example.downloadcoroutines.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.downloadcoroutines.modelClasses.PicsModel
import kotlinx.coroutines.flow.Flow

@Dao
interface PicsDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insetPics(pics: List<PicsModel>)

    @Query("DELETE FROM pics")
    suspend fun deleteAllPics()

    @Query("SELECT * FROM pics")
    fun getPics(): Flow<List<PicsModel>>
}