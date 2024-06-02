package com.sara.support.remote

import com.example.surveyheartrm.BaseClasses.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ServerApiClient {
    companion object {
        fun getApiService(): ServerApiService {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(httpLoggingInterceptor)
            httpClient.readTimeout(120, TimeUnit.SECONDS)
            httpClient.writeTimeout(120, TimeUnit.SECONDS)

            return Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
                .create(ServerApiService::class.java)
        }
    }
}