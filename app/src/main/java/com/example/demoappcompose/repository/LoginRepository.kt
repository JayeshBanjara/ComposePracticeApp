package com.example.demoappcompose.repository

import com.example.demoappcompose.data.requests.LoginRequest
import com.example.demoappcompose.data.responses.login_response.LoginResponse
import com.example.demoappcompose.di.network.SafeApiRequest
import com.example.demoappcompose.di.network.api.ApiInterface
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository @Inject constructor(
    private val apiInterface: ApiInterface
) : SafeApiRequest() {

    suspend fun login(request: LoginRequest): LoginResponse {
        return apiRequest { apiInterface.login(request) }/*.flowOn(Dispatchers.IO)*/
    }
}