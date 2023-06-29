package com.example.demoappcompose.ui.auth.login

import android.content.Context
import android.os.Build
import android.provider.Settings
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demoappcompose.data.PreferencesManager
import com.example.demoappcompose.data.requests.LoginRequest
import com.example.demoappcompose.data.responses.login_response.LoginResponse
import com.example.demoappcompose.di.network.ApiException
import com.example.demoappcompose.repository.LoginRepository
import com.example.demoappcompose.utility.Constants
import com.example.demoappcompose.utility.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val loginRepository: LoginRepository,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    val _uiState = MutableStateFlow<UiState<LoginResponse>>(UiState.Empty)
    val uiState: StateFlow<UiState<LoginResponse>> get() = _uiState

    suspend fun login(mobileNum: String, password: String) = viewModelScope.launch {

        _uiState.value = UiState.Loading

        val request = LoginRequest(
            deviceModel = Build.MODEL,
            deviceName = Build.BRAND,
            deviceNo = Settings.Secure.getString(
                context.contentResolver, Settings.Secure.ANDROID_ID
            ),
            devicePlatform = Constants.DEVICE_PLATFORM,
            deviceType = Constants.DEVICE_TYPE,
            deviceUuid = "12234",
            deviceVersion = Build.VERSION.RELEASE,
            mobileNo = mobileNum,
            password = password
        )

        try {
            val response = loginRepository.login(request = request)
            if (response.statusCode == 200) {
                preferencesManager.setLoggedIn(isLoggedIn = 1)
                preferencesManager.setUserId(userId = response.loginData.userData[0].userId.toString())
                preferencesManager.setToken(token = response.loginData.token)
                preferencesManager.setToken(token = response.loginData.userData[0].loginLogId.toString())
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
