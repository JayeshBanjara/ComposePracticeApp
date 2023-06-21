package com.example.demoappcompose.di.network.api

import com.example.demoappcompose.data.requests.LoginRequest
import com.example.demoappcompose.data.responses.LoginResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {

    @POST("login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

}