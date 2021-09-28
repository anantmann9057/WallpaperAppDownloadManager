package com.example.downloadcoroutines

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.nexogic.apiservices.ApiInterface
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App @Inject constructor() : Application() {
    var appContext: Context? = null

    var instance: App? = null

    override fun onCreate() {
        super.onCreate()
        appContext = this
        instance = this
        if (isNetworkConnected(appContext as App) == false) { (appContext as App).showToast("Network not Connected") }


    }

    fun isNetworkConnected(context: Context): Boolean {
        val connectivitymanager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkinfo = connectivitymanager.activeNetworkInfo
        return !(networkinfo == null || !networkinfo.isConnectedOrConnecting)
    }

}