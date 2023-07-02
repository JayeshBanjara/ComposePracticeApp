package com.example.demoappcompose.ui.register_purchase_book

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demoappcompose.data.PreferencesManager
import com.example.demoappcompose.data.requests.MasterDataRequest
import com.example.demoappcompose.data.requests.PurchaseBookRequest
import com.example.demoappcompose.data.responses.SuccessResponse
import com.example.demoappcompose.data.responses.purchase_book.BooksResponse
import com.example.demoappcompose.network.ApiException
import com.example.demoappcompose.network.UnAuthorisedException
import com.example.demoappcompose.repository.AppRepository
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
class PurchaseBookViewModel @Inject constructor(
    private val prefManager: PreferencesManager,
    private val appRepository: AppRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _getBookState = MutableStateFlow<UiState<BooksResponse>>(UiState.Loading)
    val getBookState: StateFlow<UiState<BooksResponse>> get() = _getBookState

    private val _uiState = MutableStateFlow<UiState<SuccessResponse>>(UiState.Empty)
    val uiState: StateFlow<UiState<SuccessResponse>> get() = _uiState


    init {
        viewModelScope.launch {
            getBookList()
        }
    }

    private suspend fun getBookList() = viewModelScope.launch {

        val list = mutableListOf<String>()
        list.add("book")

        val request = MasterDataRequest(
            deviceType = Constants.DEVICE_TYPE,
            dataRequest = list
        )

        try {
            val response =
                appRepository.getBookList(request = request)
            if (response.statusCode == 200) {
                _getBookState.value = UiState.Success(response)
            } else {
                _getBookState.value = UiState.Error(response.message)
            }
        } catch (e: ApiException) {
            _getBookState.value = UiState.Error(e.message)
        } catch (e: UnAuthorisedException) {
            prefManager.clearData()
            _uiState.value = UiState.UnAuthorised(e.message)
        } catch (e: Exception) {
            _getBookState.value = UiState.Error(e.message)
        }
    }

    suspend fun registerToPurchaseBook(
        bookId: String,
        qty: Int,
        firstName: String,
        lastName: String,
        mobileNo: String,
        email: String,
        fullAddress: String,
        city: String,
        state: String
    ) = viewModelScope.launch {

        _uiState.value = UiState.Loading

        val userId = prefManager.getUserId.first()!!
        val token = prefManager.getToken.first()!!

        val headers = HashMap<String, String>()
        headers[Constants.TOKEN_KEY] = token

        val request = PurchaseBookRequest(
            deviceType = Constants.DEVICE_TYPE,
            userId = userId,
            bookId = bookId,
            qty = qty,
            firstName = firstName,
            lastName = lastName,
            mobileNo = mobileNo,
            email = email,
            fullAddress = fullAddress,
            city = city,
            state = state
        )

        try {
            val response =
                userRepository.registerToPurchaseBook(headerMap = headers, request = request)
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
