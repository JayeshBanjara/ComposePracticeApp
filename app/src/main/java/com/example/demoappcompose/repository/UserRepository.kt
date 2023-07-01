package com.example.demoappcompose.repository

import com.example.demoappcompose.data.requests.CommonRequest
import com.example.demoappcompose.data.responses.paper_history.PaperHistoryResponse
import com.example.demoappcompose.network.ApiInterface
import com.example.demoappcompose.network.SafeApiRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val apiInterface: ApiInterface
) : SafeApiRequest() {

    suspend fun getPaperHistory(
        headerMap: Map<String, String>,
        request: CommonRequest
    ): PaperHistoryResponse {
        return apiRequest {
            apiInterface.getPaperHistory(
                headerMap = headerMap,
                request = request
            )
        }
    }
}