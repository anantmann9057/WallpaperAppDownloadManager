package com.example.downloadcoroutines.apiservices

import android.app.Application
import androidx.room.Room
import com.example.downloadcoroutines.App
import com.example.downloadcoroutines.data.PicsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {
    @Provides
    @Singleton
    fun interceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun okHttp(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor())
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(ApiInterface.BASE_URL)
        .client(okHttp())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideApi(): ApiInterface =
        provideRetrofit().create(ApiInterface::class.java)

    @Provides
    @Singleton
    fun provideDatabase(app: Application): PicsDatabase =
        Room.databaseBuilder(app, PicsDatabase::class.java, "pics_database")
            .fallbackToDestructiveMigration()
            .build()
}