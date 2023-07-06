package com.example.demoappcompose.ui.payment

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demoappcompose.data.PreferencesManager
import com.example.demoappcompose.data.requests.CommonRequest
import com.example.demoappcompose.data.requests.PaymentApproveRejectRequest
import com.example.demoappcompose.data.responses.payment.Payment
import com.example.demoappcompose.data.responses.payment.PaymentApproveRejectResponse
import com.example.demoappcompose.data.responses.payment.PaymentListResponse
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
class PaymentViewModel @Inject constructor(
    private val prefManager: PreferencesManager,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<PaymentListResponse>>(UiState.Empty)
    val uiState: StateFlow<UiState<PaymentListResponse>> get() = _uiState

    private val _approveRejectState = MutableStateFlow<UiState<PaymentApproveRejectResponse>>(UiState.Empty)
    val approveRejectState: StateFlow<UiState<PaymentApproveRejectResponse>> get() = _approveRejectState

    val paymentList = mutableStateListOf<Payment>()


    init {
        viewModelScope.launch {
            getPaymentList()
        }
    }

    private suspend fun getPaymentList() = viewModelScope.launch {

        _uiState.value = UiState.Loading

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
                userRepository.getPaymentList(headerMap = headers, request = request)
            if (response.statusCode == 200) {
                paymentList.clear()
                paymentList.addAll(response.paymentData.paymentList)
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

    suspend fun approveRejectPayment(tempSubId: Int, paymentStatus: Int) = viewModelScope.launch {

        _approveRejectState.value = UiState.Loading

        val userId = prefManager.getUserId.first()!!
        val token = prefManager.getToken.first()!!

        val headers = HashMap<String, String>()
        headers[Constants.TOKEN_KEY] = token

        val request = PaymentApproveRejectRequest(
            deviceType = Constants.DEVICE_TYPE,
            userId = userId,
            tempSubId = tempSubId.toString(),
            paymentStatus = paymentStatus
        )

        try {
            val response =
                userRepository.approveRejectPayment(headerMap = headers, request = request)
            if (response.statusCode == 200) {
                getPaymentList()
                _approveRejectState.value = UiState.Success(response)
            } else {
                _approveRejectState.value = UiState.Error(response.message)
            }
        } catch (e: ApiException) {
            _approveRejectState.value = UiState.Error(e.message)
        } catch (e: UnAuthorisedException) {
            prefManager.clearData()
            _approveRejectState.value = UiState.UnAuthorised(e.message)
        } catch (e: Exception) {
            _approveRejectState.value = UiState.Error(e.message)
        }
    }


}
