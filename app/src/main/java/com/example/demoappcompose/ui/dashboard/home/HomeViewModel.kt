package com.example.demoappcompose.ui.dashboard.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demoappcompose.data.PreferencesManager
import com.example.demoappcompose.data.requests.CommonRequest
import com.example.demoappcompose.data.requests.LogoutRequest
import com.example.demoappcompose.data.responses.dashboard_response.DashboardResponse
import com.example.demoappcompose.data.responses.logout.LogoutResponse
import com.example.demoappcompose.network.ApiException
import com.example.demoappcompose.network.UnAuthorisedException
import com.example.demoappcompose.repository.HomeRepository
import com.example.demoappcompose.utility.Constants
import com.example.demoappcompose.utility.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val prefManager: PreferencesManager,
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<DashboardResponse>>(UiState.Loading)
    val uiState: StateFlow<UiState<DashboardResponse>> get() = _uiState

    private val _logoutState = MutableStateFlow<UiState<LogoutResponse>>(UiState.Empty)
    val logoutState: StateFlow<UiState<LogoutResponse>> get() = _logoutState

    init {
        viewModelScope.launch {
            getDashboardData()
        }
    }

    private suspend fun getDashboardData() = viewModelScope.launch {

        val userId = prefManager.getUserId.first()!!
        val token = prefManager.getToken.first()!!

        val headers = HashMap<String, String>()
        headers[Constants.TOKEN_KEY] = token

        val request = CommonRequest(
            deviceType = Constants.DEVICE_TYPE,
            userId = userId,
        )

        try {
            val response = homeRepository.getDashboardData(headerMap = headers, request = request)
            if (response.statusCode == 200) {
                _uiState.value = UiState.Success(response)
            } else {
                _uiState.value = UiState.Error(response.message)
            }
        } catch (e: ApiException) {
            _uiState.value = UiState.Error(e.message)
        } catch (e: Exception) {
            _uiState.value = UiState.Error(e.message)
        }
    }

    suspend fun logout() = viewModelScope.launch {

        _logoutState.value = UiState.Loading

        val userId = prefManager.getUserId.first()!!
        val token = prefManager.getToken.first()!!
        val loginLogId = prefManager.getLoginLogId.first()!!

        val headers = HashMap<String, String>()
        headers[Constants.TOKEN_KEY] = token

        val request = LogoutRequest(
            deviceType = Constants.DEVICE_TYPE,
            userId = userId,
            loginLogId = loginLogId,
        )

        try {
            val response = homeRepository.logout(headerMap = headers, request = request)
            if (response.statusCode == 200) {
                prefManager.clearData()
                _logoutState.value = UiState.Success(response)
            } else {
                _logoutState.value = UiState.Error(response.statusMessage)
            }
        } catch (e: ApiException) {
            _logoutState.value = UiState.Error(e.message)
        } catch (e: Exception) {
            _logoutState.value = UiState.Error(e.message)
        }
    }
}
