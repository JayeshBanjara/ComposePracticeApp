package com.example.demoappcompose.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demoappcompose.data.requests.ConfigRequest
import com.example.demoappcompose.data.responses.config.ConfigResponse
import com.example.demoappcompose.network.ApiException
import com.example.demoappcompose.network.UnAuthorisedException
import com.example.demoappcompose.repository.AppRepository
import com.example.demoappcompose.utility.Constants
import com.example.demoappcompose.utility.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<ConfigResponse>>(UiState.Loading)
    val uiState: StateFlow<UiState<ConfigResponse>> get() = _uiState

    init {
        viewModelScope.launch {
            getConfig()
        }
    }

    private suspend fun getConfig() = viewModelScope.launch {

        val request = ConfigRequest(
            deviceType = Constants.DEVICE_TYPE,
            configType = "7"
        )

        try {
            val response = appRepository.getConfig(request = request)
            if (response.statusCode == 200) {
                _uiState.value = UiState.Success(response)
            } else {
                _uiState.value = UiState.Error(response.message)
            }
        } catch (e: ApiException) {
            _uiState.value = UiState.Error(e.message)
        } catch (e: UnAuthorisedException) {
            _uiState.value = UiState.UnAuthorised(e.message)
        } catch (e: Exception) {
            _uiState.value = UiState.Error(e.message)
        }
    }

}
