package com.example.demoappcompose.di.network.api

import com.example.demoappcompose.data.requests.CommonRequest
import com.example.demoappcompose.data.requests.LoginRequest
import com.example.demoappcompose.data.requests.LogoutRequest
import com.example.demoappcompose.data.requests.MasterDataRequest
import com.example.demoappcompose.data.requests.RegisterRequest
import com.example.demoappcompose.data.requests.SubjectListRequest
import com.example.demoappcompose.data.responses.dashboard_response.DashboardResponse
import com.example.demoappcompose.data.responses.login_response.LoginResponse
import com.example.demoappcompose.data.responses.logout.LogoutResponse
import com.example.demoappcompose.data.responses.my_subscription.SubscriptionListResponse
import com.example.demoappcompose.data.responses.register_response.GetRoleMediumDataResponse
import com.example.demoappcompose.data.responses.register_response.RegisterResponse
import com.example.demoappcompose.data.responses.subjects.SubjectListResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.HeaderMap
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

    @POST("user/get-dashboard-data")
    suspend fun getDashboardData(
        @HeaderMap headerMap: Map<String, String>,
        @Body request: CommonRequest
    ): Response<DashboardResponse>

    @POST("user/get-my-subscription-data")
    suspend fun getSubscriptionList(
        @HeaderMap headerMap: Map<String, String>,
        @Body request: CommonRequest
    ): Response<SubscriptionListResponse>

    @POST("user/logout")
    suspend fun logout(
        @HeaderMap headerMap: Map<String, String>,
        @Body request: LogoutRequest
    ): Response<LogoutResponse>

    @POST("user/get-subject-data")
    suspend fun getSubjects(
        @HeaderMap headerMap: Map<String, String>,
        @Body request: SubjectListRequest
    ): Response<SubjectListResponse>

}