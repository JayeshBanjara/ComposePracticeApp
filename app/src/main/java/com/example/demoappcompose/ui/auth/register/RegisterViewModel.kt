package com.example.demoappcompose.ui.auth.register

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demoappcompose.data.requests.MasterDataRequest
import com.example.demoappcompose.data.requests.RegisterRequest
import com.example.demoappcompose.data.responses.register_response.GetRoleMediumDataResponse
import com.example.demoappcompose.data.responses.register_response.RegisterResponse
import com.example.demoappcompose.network.ApiException
import com.example.demoappcompose.network.UnAuthorisedException
import com.example.demoappcompose.repository.RegisterRepository
import com.example.demoappcompose.utility.Constants
import com.example.demoappcompose.utility.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val registerRepository: RegisterRepository
) : ViewModel() {

    private val _getRolesState = MutableStateFlow<UiState<GetRoleMediumDataResponse>>(UiState.Empty)
    val getRolesState: StateFlow<UiState<GetRoleMediumDataResponse>> get() = _getRolesState

    private val _registerState = MutableStateFlow<UiState<RegisterResponse>>(UiState.Empty)
    val registerState: StateFlow<UiState<RegisterResponse>> get() = _registerState

    init {
        viewModelScope.launch {
            getMasterData()
        }
    }

    private suspend fun getMasterData() = viewModelScope.launch {

        _getRolesState.value = UiState.Loading

        val list = mutableListOf<String>()
        list.add("role")
        list.add("medium")

        val request = MasterDataRequest(
            deviceType = Constants.DEVICE_TYPE,
            dataRequest = list,
        )

        try {
            val response = registerRepository.getMasterData(request = request)
            if (response.statusCode == 200) {
                _getRolesState.value = UiState.Success(response)
            } else {
                _getRolesState.value = UiState.Error(response.message)
            }
        } catch (e: ApiException) {
            _getRolesState.value = UiState.Error(e.message)
        } catch (e: UnAuthorisedException) {
            _getRolesState.value = UiState.UnAuthorised(e.message)
        } catch (e: Exception) {
            _getRolesState.value = UiState.Error(e.message)
        }
    }

    suspend fun register(
        mobileNo: String,
        fullName: String,
        email: String,
        password: String,
        instituteName: String,
        instituteLogo: Uri,
        city: String,
        roleId: String,
        mediumIds: List<String>
    ) = viewModelScope.launch {

        _registerState.value = UiState.Loading

        val insLogo = encode(instituteLogo)

        val request = RegisterRequest(
            deviceType = Constants.DEVICE_TYPE,
            mobileNo = mobileNo,
            fullName = fullName,
            email = email,
            password = password,
            instituteName = instituteName,
            instituteLogo = "data:image/jpeg;base64,$insLogo",
            city = city,
            roleId = roleId,
            mediumId = mediumIds
        )

        try {
            val response = registerRepository.register(request = request)
            if (response.statusCode == 200) {
                _registerState.value = UiState.Success(response)
            } else {
                _registerState.value = UiState.Error(response.message)
            }
        } catch (e: ApiException) {
            _registerState.value = UiState.Error(e.message)
        } catch (e: UnAuthorisedException) {
            _registerState.value = UiState.UnAuthorised(e.message)
        } catch (e: Exception) {
            _registerState.value = UiState.Error(e.message)
        }
    }

    private fun encode(imageUri: Uri): String {
        val input = context.contentResolver.openInputStream(imageUri)
        val image = BitmapFactory.decodeStream(input, null, null)
        // Encode image to base64 string
        val baos = ByteArrayOutputStream()
        image?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val imageBytes = baos.toByteArray()
        return Base64.encodeToString(imageBytes, Base64.DEFAULT)
    }

}
