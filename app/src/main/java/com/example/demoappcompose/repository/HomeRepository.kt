package com.example.demoappcompose.repository

import com.example.demoappcompose.data.requests.CommonRequest
import com.example.demoappcompose.data.requests.LogoutRequest
import com.example.demoappcompose.data.responses.dashboard_response.DashboardResponse
import com.example.demoappcompose.data.responses.logout.LogoutResponse
import com.example.demoappcompose.di.network.SafeApiRequest
import com.example.demoappcompose.di.network.api.ApiInterface
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepository @Inject constructor(
    private val apiInterface: ApiInterface
) : SafeApiRequest() {

    suspend fun getDashboardData(
        headerMap: Map<String, String>,
        request: CommonRequest
    ): DashboardResponse {
        return apiRequest {
            apiInterface.getDashboardData(
                headerMap = headerMap,
                request = request
            )
        }
    }

    suspend fun logout(
        headerMap: Map<String, String>,
        request: LogoutRequest
    ): LogoutResponse {
        return apiRequest {
            apiInterface.logout(
                headerMap = headerMap,
                request = request
            )
        }
    }
}