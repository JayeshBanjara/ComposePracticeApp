package com.example.demoappcompose.di.network.api

import com.example.demoappcompose.data.requests.LoginRequest
import com.example.demoappcompose.data.requests.MasterDataRequest
import com.example.demoappcompose.data.requests.RegisterRequest
import com.example.demoappcompose.data.responses.login_response.LoginResponse
import com.example.demoappcompose.data.responses.register_response.GetRoleMediumDataResponse
import com.example.demoappcompose.data.responses.register_response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {

    @POST("login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @POST("get-master-data")
    suspend fun getMasterData(
        @Body request: MasterDataRequest
    ): Response<GetRoleMediumDataResponse>

    @POST("register")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<RegisterResponse>

}