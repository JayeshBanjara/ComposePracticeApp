package com.example.demoappcompose.repository

import com.example.demoappcompose.data.requests.ConfigRequest
import com.example.demoappcompose.data.requests.MasterDataRequest
import com.example.demoappcompose.data.responses.about_us.AboutUsResponse
import com.example.demoappcompose.data.responses.config.ConfigResponse
import com.example.demoappcompose.data.responses.purchase_book.BooksResponse
import com.example.demoappcompose.network.ApiInterface
import com.example.demoappcompose.network.SafeApiRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepository @Inject constructor(
    private val apiInterface: ApiInterface
) : SafeApiRequest() {

    suspend fun getConfig(request: ConfigRequest): ConfigResponse {
        return apiRequest { apiInterface.getConfig(request) }
    }

    suspend fun getAboutUsData(request: MasterDataRequest): AboutUsResponse {
        return apiRequest { apiInterface.getAboutUsData(request) }
    }

    suspend fun getBookList(request: MasterDataRequest): BooksResponse {
        return apiRequest { apiInterface.getBookList(request) }
    }
}