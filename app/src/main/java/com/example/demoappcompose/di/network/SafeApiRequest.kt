package com.example.demoappcompose.di.network

import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

abstract class SafeApiRequest {

    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {

        val response = call.invoke()
        if (response.isSuccessful) {
            return response.body()!!
        } else {

            val error = response.errorBody()?.string()

            val message = StringBuilder()

            error?.let {
                try {
                    message.append(JSONObject(it).getString("message"))
                } catch (e: JSONException) {
                }
            }

            if (response.code() == 401) {
                throw UnAuthorisedException(message.toString())
            } else {
                throw ApiException(message.toString())
            }

        }
    }
}