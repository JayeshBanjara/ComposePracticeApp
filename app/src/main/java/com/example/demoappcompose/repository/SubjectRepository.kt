package com.example.demoappcompose.repository

import com.example.demoappcompose.data.requests.SubjectListRequest
import com.example.demoappcompose.data.responses.subjects.SubjectListResponse
import com.example.demoappcompose.network.SafeApiRequest
import com.example.demoappcompose.network.ApiInterface
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SubjectRepository @Inject constructor(
    private val apiInterface: ApiInterface
) : SafeApiRequest() {

    suspend fun getSubjects(
        headerMap: Map<String, String>,
        request: SubjectListRequest
    ): SubjectListResponse {
        return apiRequest {
            apiInterface.getSubjects(
                headerMap = headerMap,
                request = request
            )
        }
    }
}