package com.sara.support.repository


import com.google.gson.JsonObject

interface UserRepository {
    suspend fun getAllList(): JsonObject
}