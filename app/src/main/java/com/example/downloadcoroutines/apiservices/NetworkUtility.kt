package com.nexogic.apiservices

import android.content.Context
import android.net.ConnectivityManager
import com.example.downloadcoroutines.utils.Preferences
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

object NetworkUtility {
    fun isNetworkConnected(context: Context): Boolean {
        val connectivitymanager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkinfo = connectivitymanager.activeNetworkInfo
        return !(networkinfo == null || !networkinfo.isConnectedOrConnecting)
    }

    class Builder(baseURL: String, mContext : Context) {
        private var BASE_URL = ""
        private var context = mContext
        private var connectionTimeout: Long = 0
        private var readTimeout: Long = 0
        private var writeTimeout: Long = 0
        private var shouldRetryOnConnectionFailure = false
        var retrofit: Retrofit? = null
            private set

        /**
         * Sets the default connect timeout in Seconds for new connections.
         */
        fun withConnectionTimeout(connectionTimeout: Long): Builder {
            this.connectionTimeout = connectionTimeout
            return this
        }

        /**
         * Sets the default read timeout in Minutes for new connections.
         */
        fun withReadTimeout(readTimeout: Long): Builder {
            this.readTimeout = readTimeout
            return this
        }

        /**
         * Sets the default write timeout in Minutes for new connections.
         */
        fun withWriteTimeout(writeTimeout: Long): Builder {
            this.writeTimeout = writeTimeout
            return this
        }

        /**
         * Configures the Retrofit client to retry or not when a connectivity problem is encountered.
         */
        fun withShouldRetryOnConnectionFailure(shouldRetryOnConnectionFailure: Boolean): Builder {
            this.shouldRetryOnConnectionFailure = shouldRetryOnConnectionFailure
            return this
        }

        /**
         * Create the Retrofit instance using the configured values.
         */
        fun build(): NetworkUtility {
            setupForAPI()
            return NetworkUtility
        }

        private fun setupForAPI() {

            val builder = OkHttpClient.Builder()
            builder.addInterceptor(object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): Response {
                    val original = chain.request()

                    // Request customization: add request headers
                    val requestBuilder = original.newBuilder()
                        .header("Authorization", "Bearer " + Preferences(context).getUserToken())
                        /*.header("Device-Os", "android")
                        .header("API-Version", BuildConfig.VERSION_NAME)*/
                    val request = requestBuilder.build()
                    return chain.proceed(request)
                }
            })
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            builder.addInterceptor(httpLoggingInterceptor)
            val okHttpClient = builder.build()


            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            /*val builder = OkHttpClient.Builder()
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            builder.connectTimeout(connectionTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.MINUTES)
                .writeTimeout(writeTimeout, TimeUnit.MINUTES)
                .retryOnConnectionFailure(shouldRetryOnConnectionFailure)
                .addInterceptor(httpLoggingInterceptor)
            val gson = GsonBuilder().setLenient().create()
            val okHttpClient = builder.build()
            retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()*/
        }

        init {
            BASE_URL = baseURL
        }
    }
}