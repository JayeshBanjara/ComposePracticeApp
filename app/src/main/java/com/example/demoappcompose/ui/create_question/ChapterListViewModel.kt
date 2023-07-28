package com.example.demoappcompose.ui.create_question

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demoappcompose.data.PreferencesManager
import com.example.demoappcompose.data.requests.GeneratePaymentRequest
import com.example.demoappcompose.data.requests.HeadingListRequest
import com.example.demoappcompose.data.responses.SuccessResponse
import com.example.demoappcompose.data.responses.chapter_list.ChapterListResponse
import com.example.demoappcompose.network.ApiException
import com.example.demoappcompose.network.UnAuthorisedException
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
class ChapterListViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val prefManager: PreferencesManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<ChapterListResponse>>(UiState.Empty)
    val uiState: StateFlow<UiState<ChapterListResponse>> get() = _uiState

    private val _generatePaymentState = MutableStateFlow<UiState<SuccessResponse>>(UiState.Empty)
    val generatePaymentState: StateFlow<UiState<SuccessResponse>> get() = _generatePaymentState



    suspend fun getChapterList(classId: String, subjectId: String) = viewModelScope.launch {

        val userId = prefManager.getUserId.first()!!
        val token = prefManager.getToken.first()!!

        val headers = HashMap<String, String>()
        headers[Constants.TOKEN_KEY] = token

        val request = HeadingListRequest(
            deviceType = Constants.DEVICE_TYPE,
            userId = userId,
            classId = classId,
            subjectId = subjectId
        )

        try {
            val response = userRepository.getChapterList(headerMap = headers, request = request)
            if (response.statusCode == 200) {
                _uiState.value = UiState.Success(response)
            } else {
                _uiState.value = UiState.Error(response.message)
            }
        } catch (e: UnAuthorisedException) {
            prefManager.clearData()
            _uiState.value = UiState.UnAuthorised(e.message)
        } catch (e: ApiException) {
            _uiState.value = UiState.Error(e.message)
        } catch (e: Exception) {
            _uiState.value = UiState.Error(e.message)
        }
    }

    suspend fun generaTePaymentRequest(classId: String, subjectId: String, amount: String) = viewModelScope.launch {

        val userId = prefManager.getUserId.first()!!
        val token = prefManager.getToken.first()!!

        val headers = HashMap<String, String>()
        headers[Constants.TOKEN_KEY] = token

        val request = GeneratePaymentRequest(
            deviceType = Constants.DEVICE_TYPE,
            userId = userId,
            classId = classId,
            subjectId = subjectId,
            amount = amount
        )

        try {
            val response = userRepository.generaTePaymentRequest(headerMap = headers, request = request)
            if (response.statusCode == 200) {
                _generatePaymentState.value = UiState.Success(response)
            } else {
                _generatePaymentState.value = UiState.Error(response.message)
            }
        } catch (e: UnAuthorisedException) {
            prefManager.clearData()
            _generatePaymentState.value = UiState.UnAuthorised(e.message)
        } catch (e: ApiException) {
            _generatePaymentState.value = UiState.Error(e.message)
        } catch (e: Exception) {
            _generatePaymentState.value = UiState.Error(e.message)
        }
    }
}
