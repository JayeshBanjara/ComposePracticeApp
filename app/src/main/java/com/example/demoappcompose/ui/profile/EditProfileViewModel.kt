package com.example.demoappcompose.ui.profile

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demoappcompose.data.PreferencesManager
import com.example.demoappcompose.data.requests.UpdateProfileRequest
import com.example.demoappcompose.data.responses.profile.ProfileResponse
import com.example.demoappcompose.network.ApiException
import com.example.demoappcompose.network.UnAuthorisedException
import com.example.demoappcompose.repository.UserRepository
import com.example.demoappcompose.utility.Constants
import com.example.demoappcompose.utility.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val prefManager: PreferencesManager,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<ProfileResponse>>(UiState.Empty)
    val uiState: StateFlow<UiState<ProfileResponse>> get() = _uiState

    private val _numberState = MutableStateFlow("")
    val numberState: StateFlow<String> get() = _numberState

    private val _nameState = MutableStateFlow("")
    val nameState: StateFlow<String> get() = _nameState

    private val _emailState = MutableStateFlow("")
    val emailState: StateFlow<String> get() = _emailState

    private val _profilePicState = MutableStateFlow("")
    val profilePicState: StateFlow<String> get() = _profilePicState

    init {
        viewModelScope.launch {
            _numberState.value = prefManager.getUserMobileNumber.first()!!
            _nameState.value = prefManager.getUserFullName.first()!!
            _emailState.value = prefManager.getEmail.first()!!
            _profilePicState.value = prefManager.getUserProfileImage.first()!!
        }
    }

    suspend fun updateProfile(
        email: String,
        fullName: String,
        mobileNo: String,
        insLogoUri: Uri?,
    ) = viewModelScope.launch {

        _uiState.value = UiState.Loading

        val userId = prefManager.getUserId.first()!!
        val token = prefManager.getToken.first()!!

        val headers = HashMap<String, String>()
        headers[Constants.TOKEN_KEY] = token

        val insLogo: String? = if (insLogoUri != null) encode(insLogoUri) else null

        val request = UpdateProfileRequest(
            deviceType = Constants.DEVICE_TYPE,
            userId = userId,
            email = email,
            fullName = fullName,
            mobileNo = mobileNo,
            instituteLogo = if (insLogo != null) "data:image/jpeg;base64,$insLogo" else ""
        )

        try {
            val response =
                userRepository.updateProfile(headerMap = headers, request = request)
            if (response.statusCode == 200) {
                val profile = response.profileData.userData
                prefManager.apply {
                    setUserMobileNumber(profile.mobileNo)
                    setEmail(profile.email)
                    setUserFullName(profile.fullName)
                    setUserProfileImage(profile.largeImageUrl)
                }

                _numberState.value = prefManager.getUserMobileNumber.first()!!
                _nameState.value = prefManager.getUserFullName.first()!!
                _emailState.value = prefManager.getEmail.first()!!
                _profilePicState.value = prefManager.getUserProfileImage.first()!!

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
