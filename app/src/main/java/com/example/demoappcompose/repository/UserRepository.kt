package com.example.demoappcompose.repository

import com.example.demoappcompose.data.requests.CommonRequest
import com.example.demoappcompose.data.requests.HeadingListRequest
import com.example.demoappcompose.data.requests.PurchaseBookRequest
import com.example.demoappcompose.data.requests.UpdateProfileRequest
import com.example.demoappcompose.data.responses.SuccessResponse
import com.example.demoappcompose.data.responses.chapter_list.ChapterListResponse
import com.example.demoappcompose.data.responses.paper_history.PaperHistoryResponse
import com.example.demoappcompose.data.responses.profile.ProfileResponse
import com.example.demoappcompose.data.responses.questions.HeadingListResponse
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

    suspend fun updateProfile(
        headerMap: Map<String, String>,
        request: UpdateProfileRequest
    ): ProfileResponse {
        return apiRequest {
            apiInterface.updateProfile(
                headerMap = headerMap,
                request = request
            )
        }
    }

    suspend fun registerToPurchaseBook(
        headerMap: Map<String, String>,
        request: PurchaseBookRequest
    ): SuccessResponse {
        return apiRequest {
            apiInterface.registerToPurchaseBook(
                headerMap = headerMap,
                request = request
            )
        }
    }

    suspend fun getHeadingList(
        headerMap: Map<String, String>,
        request: HeadingListRequest
    ): HeadingListResponse {
        return apiRequest {
            apiInterface.getHeadingList(
                headerMap = headerMap,
                request = request
            )
        }
    }

    suspend fun getChapterList(
        headerMap: Map<String, String>,
        request: HeadingListRequest
    ): ChapterListResponse {
        return apiRequest {
            apiInterface.getChapterList(
                headerMap = headerMap,
                request = request
            )
        }
    }
}