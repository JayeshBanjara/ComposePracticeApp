package com.example.demoappcompose.ui.dashboard.my_subscription

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demoappcompose.data.PreferencesManager
import com.example.demoappcompose.data.requests.CommonRequest
import com.example.demoappcompose.data.responses.my_subscription.SubscriptionListResponse
import com.example.demoappcompose.network.ApiException
import com.example.demoappcompose.network.UnAuthorisedException
import com.example.demoappcompose.repository.SubscriptionRepository
import com.example.demoappcompose.utility.Constants
import com.example.demoappcompose.utility.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MySubscriptionViewModel @Inject constructor(
    private val prefManager: PreferencesManager,
    private val subscriptionRepository: SubscriptionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<SubscriptionListResponse>>(UiState.Loading)
    val uiState: StateFlow<UiState<SubscriptionListResponse>> get() = _uiState

    init {
        viewModelScope.launch {
            getSubscriptionList()
        }
    }

    private suspend fun getSubscriptionList() = viewModelScope.launch {

        val userId = prefManager.getUserId.first()!!
        val token = prefManager.getToken.first()!!

        val headers = HashMap<String, String>()
        headers[Constants.TOKEN_KEY] = token

        val request = CommonRequest(
            deviceType = Constants.DEVICE_TYPE,
            userId = userId,
        )

        try {
            val response =
                subscriptionRepository.getSubscriptionList(headerMap = headers, request = request)
            if (response.statusCode == 200) {
                _uiState.value = UiState.Success(response)
            } else {
                _uiState.value = UiState.Error(response.message)
            }
        } catch (e: ApiException) {
            _uiState.value = UiState.Error(e.message)
        } catch (e: UnAuthorisedException) {
            prefManager.clearData()
            _uiState.value = UiState.UnAuthorised(e.message)
        } catch (e: Exception) {
            _uiState.value = UiState.Error(e.message)
        }
    }
}
