package com.example.demoappcompose.repository

import com.example.demoappcompose.data.requests.CommonRequest
import com.example.demoappcompose.data.responses.my_subscription.SubscriptionListResponse
import com.example.demoappcompose.network.SafeApiRequest
import com.example.demoappcompose.network.ApiInterface
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SubscriptionRepository @Inject constructor(
    private val apiInterface: ApiInterface
) : SafeApiRequest() {

    suspend fun getSubscriptionList(
        headerMap: Map<String, String>,
        request: CommonRequest
    ): SubscriptionListResponse {
        return apiRequest {
            apiInterface.getSubscriptionList(
                headerMap = headerMap,
                request = request
            )
        }
    }
}