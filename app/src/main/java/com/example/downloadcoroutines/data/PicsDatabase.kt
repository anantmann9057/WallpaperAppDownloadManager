package com.example.downloadcoroutines.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.downloadcoroutines.data.PicsDAO
import com.example.downloadcoroutines.modelClasses.PicsModel

@Database(entities = [PicsModel::class], version = 1)
abstract class PicsDatabase : RoomDatabase() {
    abstract fun picsDao(): PicsDAO
}