package com.example.downloadcoroutines

import android.app.Application
import android.content.Context
import com.nexogic.apiservices.ApiInterface
import com.nexogic.apiservices.NetworkUtility

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

    private fun basicSetupForAPI() {
        val networkBuilder = NetworkUtility.Builder(BASE_URL, appContext!!)
        networkBuilder.withConnectionTimeout(60)
            .withReadTimeout(10)
            .withShouldRetryOnConnectionFailure(true)
            .build()
        apiService = networkBuilder.retrofit!!.create(ApiInterface::class.java)
    }
}