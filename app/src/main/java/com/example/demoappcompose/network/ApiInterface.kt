package com.example.demoappcompose.network

import com.example.demoappcompose.data.requests.CommonRequest
import com.example.demoappcompose.data.requests.ConfigRequest
import com.example.demoappcompose.data.requests.HeadingListRequest
import com.example.demoappcompose.data.requests.LoginRequest
import com.example.demoappcompose.data.requests.LogoutRequest
import com.example.demoappcompose.data.requests.MasterDataRequest
import com.example.demoappcompose.data.requests.PaymentApproveRejectRequest
import com.example.demoappcompose.data.requests.PurchaseBookRequest
import com.example.demoappcompose.data.requests.RegisterRequest
import com.example.demoappcompose.data.requests.SubjectListRequest
import com.example.demoappcompose.data.requests.UpdateProfileRequest
import com.example.demoappcompose.data.responses.SuccessResponse
import com.example.demoappcompose.data.responses.about_us.AboutUsResponse
import com.example.demoappcompose.data.responses.config.ConfigResponse
import com.example.demoappcompose.data.responses.dashboard_response.DashboardResponse
import com.example.demoappcompose.data.responses.login_response.LoginResponse
import com.example.demoappcompose.data.responses.logout.LogoutResponse
import com.example.demoappcompose.data.responses.my_subscription.SubscriptionListResponse
import com.example.demoappcompose.data.responses.paper_history.PaperHistoryResponse
import com.example.demoappcompose.data.responses.payment.PaymentApproveRejectResponse
import com.example.demoappcompose.data.responses.payment.PaymentListResponse
import com.example.demoappcompose.data.responses.profile.ProfileResponse
import com.example.demoappcompose.data.responses.purchase_book.BooksResponse
import com.example.demoappcompose.data.responses.questions.HeadingListResponse
import com.example.demoappcompose.data.responses.register_response.GetRoleMediumDataResponse
import com.example.demoappcompose.data.responses.register_response.RegisterResponse
import com.example.demoappcompose.data.responses.subjects.SubjectListResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface ApiInterface {

    @POST("get-settings")
    suspend fun getConfig(
        @Body request: ConfigRequest
    ): Response<ConfigResponse>

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

    @POST("get-master-data")
    suspend fun getAboutUsData(
        @Body request: MasterDataRequest
    ): Response<AboutUsResponse>

    @POST("user/get-my-paper-history-data")
    suspend fun getPaperHistory(
        @HeaderMap headerMap: Map<String, String>,
        @Body request: CommonRequest
    ): Response<PaperHistoryResponse>

    @POST("user/update-user-profile-data")
    suspend fun updateProfile(
        @HeaderMap headerMap: Map<String, String>,
        @Body request: UpdateProfileRequest
    ): Response<ProfileResponse>

    @POST("get-master-data")
    suspend fun getBookList(
        @Body request: MasterDataRequest
    ): Response<BooksResponse>

    @POST("user/register-to-book-purchase")
    suspend fun registerToPurchaseBook(
        @HeaderMap headerMap: Map<String, String>,
        @Body request: PurchaseBookRequest
    ): Response<SuccessResponse>

    @POST("user/get-subject-heading-data")
    suspend fun getHeadingList(
        @HeaderMap headerMap: Map<String, String>,
        @Body request: HeadingListRequest
    ): Response<HeadingListResponse>

    @POST("user/get-chapter-data")
    suspend fun getChapterList(
        @HeaderMap headerMap: Map<String, String>,
        @Body request: HeadingListRequest
    ): Response<ChapterListResponse>

    @POST("user/get-payment-data")
    suspend fun getPaymentList(
        @HeaderMap headerMap: Map<String, String>,
        @Body request: CommonRequest
    ): Response<PaymentListResponse>

    @POST("user/subscription-approve-or-reject")
    suspend fun approveRejectPayment(
        @HeaderMap headerMap: Map<String, String>,
        @Body request: PaymentApproveRejectRequest
    ): Response<PaymentApproveRejectResponse>
}