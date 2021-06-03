package com.example.downloadcoroutines

import android.app.Application
import android.content.Context
import com.nexogic.apiservices.ApiInterface
import com.nexogic.apiservices.NetworkUtility
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class App : Application() {
    var appContext: Context? = null

    var instance: App? = null

    private val TAG = App::class.java.name

    var apiService: ApiInterface? = null


    private val BASE_URL = "https://picsum.photos/"
    override fun onCreate() {
        super.onCreate()
        appContext = this
        instance = this
        if (NetworkUtility.isNetworkConnected(appContext as App)==false){
            showToast("Network not Connected")
        }
        basicSetupForAPI()


    }

    @Provides
    @Singleton
    private fun basicSetupForAPI() {
        val networkBuilder = NetworkUtility.Builder(BASE_URL, appContext!!)
        networkBuilder.withConnectionTimeout(60)
            .withReadTimeout(10)
            .withShouldRetryOnConnectionFailure(true)
            .build()
        apiService = networkBuilder.retrofit!!.create(ApiInterface::class.java)
    }
}