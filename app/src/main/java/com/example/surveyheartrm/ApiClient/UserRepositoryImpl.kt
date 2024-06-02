package com.sara.support.repository


import com.google.gson.JsonObject
import com.sara.support.remote.ServerApiClient

class UserRepositoryImpl : UserRepository {

    private val serverApiClient = ServerApiClient.getApiService()

    override suspend fun getAllList(): JsonObject {
        var response = serverApiClient.getAllList()
        return response
    }


}