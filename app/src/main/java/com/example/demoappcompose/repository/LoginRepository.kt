package com.example.demoappcompose.repository

import com.example.demoappcompose.data.requests.LoginRequest
import com.example.demoappcompose.data.responses.LoginResponse
import com.example.demoappcompose.di.network.SafeApiRequest
import com.example.demoappcompose.di.network.api.LoginApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository @Inject constructor(
    private val loginApi: LoginApi
): SafeApiRequest() {

    suspend fun login(request: LoginRequest): LoginResponse {
        return apiRequest { loginApi.login(request) }/*.flowOn(Dispatchers.IO)*/
    }
}