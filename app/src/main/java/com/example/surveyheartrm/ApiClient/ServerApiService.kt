package com.sara.support.remote


import com.example.surveyheartrm.BaseClasses.Constants
import com.google.gson.JsonObject
import retrofit2.http.GET

interface ServerApiService {

    @GET(Constants.Todos)
    suspend fun getAllList(): JsonObject


}