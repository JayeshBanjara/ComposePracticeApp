package com.example.demoappcompose.repository

import com.example.demoappcompose.data.requests.MasterDataRequest
import com.example.demoappcompose.data.requests.RegisterRequest
import com.example.demoappcompose.data.responses.register_response.GetRoleMediumDataResponse
import com.example.demoappcompose.data.responses.register_response.RegisterResponse
import com.example.demoappcompose.di.network.SafeApiRequest
import com.example.demoappcompose.di.network.api.ApiInterface
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RegisterRepository @Inject constructor(
    private val apiInterface: ApiInterface
) : SafeApiRequest() {

    suspend fun getMasterData(request: MasterDataRequest): GetRoleMediumDataResponse {
        return apiRequest { apiInterface.getMasterData(request) }
    }

    suspend fun register(request: RegisterRequest): RegisterResponse {
        return apiRequest { apiInterface.register(request) }
    }
}