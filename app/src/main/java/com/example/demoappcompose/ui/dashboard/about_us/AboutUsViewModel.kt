package com.example.demoappcompose.ui.dashboard.about_us

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demoappcompose.data.PreferencesManager
import com.example.demoappcompose.data.requests.MasterDataRequest
import com.example.demoappcompose.data.responses.about_us.AboutUsResponse
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
class AboutUsViewModel @Inject constructor(
    private val appRepository: AppRepository,
    private val prefManager: PreferencesManager
) : ViewModel() {

    private val _state = MutableStateFlow<UiState<AboutUsResponse>>(UiState.Empty)
    val state: StateFlow<UiState<AboutUsResponse>> get() = _state

    init {
        viewModelScope.launch {
            getMasterData()
        }
    }

    private suspend fun getMasterData() = viewModelScope.launch {

        _state.value = UiState.Loading

        val list = mutableListOf<String>()
        list.add("cmspage")

        val request = MasterDataRequest(
            deviceType = Constants.DEVICE_TYPE,
            dataRequest = list,
        )

        try {
            val response = appRepository.getAboutUsData(request = request)
            if (response.statusCode == 200) {
                _state.value = UiState.Success(response)
            } else {
                _state.value = UiState.Error(response.message)
            }
        } catch (e: UnAuthorisedException) {
            prefManager.clearData()
            _state.value = UiState.UnAuthorised(e.message)
        } catch (e: ApiException) {
            _state.value = UiState.Error(e.message)
        } catch (e: Exception) {
            _state.value = UiState.Error(e.message)
        }
    }
}
