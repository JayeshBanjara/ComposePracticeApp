package com.example.demoappcompose.ui.paper_history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demoappcompose.data.PreferencesManager
import com.example.demoappcompose.data.requests.CommonRequest
import com.example.demoappcompose.data.responses.paper_history.PaperHistoryResponse
import com.example.demoappcompose.network.ApiException
import com.example.demoappcompose.repository.UserRepository
import com.example.demoappcompose.utility.Constants
import com.example.demoappcompose.utility.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaperHistoryViewModel @Inject constructor(
    private val prefManager: PreferencesManager,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<PaperHistoryResponse>>(UiState.Loading)
    val uiState: StateFlow<UiState<PaperHistoryResponse>> get() = _uiState

    init {
        viewModelScope.launch {
            getPaperHistory()
        }
    }

    private suspend fun getPaperHistory() = viewModelScope.launch {

        val userId = prefManager.getUserId.first()!!
        val token = prefManager.getToken.first()!!

        val headers = HashMap<String, String>()
        headers[Constants.TOKEN_KEY] = token

        val request = CommonRequest(
            deviceType = Constants.DEVICE_TYPE,
            userId = userId
        )

        try {
            val response =
                userRepository.getPaperHistory(headerMap = headers, request = request)
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
}
