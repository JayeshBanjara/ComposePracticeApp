package com.example.demoappcompose.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demoappcompose.data.PreferencesManager
import com.example.demoappcompose.data.requests.CommonRequest
import com.example.demoappcompose.data.responses.my_subscription.SubscriptionListResponse
import com.example.demoappcompose.network.ApiException
import com.example.demoappcompose.repository.SubscriptionRepository
import com.example.demoappcompose.utility.Constants
import com.example.demoappcompose.utility.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val prefManager: PreferencesManager,
    private val subscriptionRepository: SubscriptionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<SubscriptionListResponse>>(UiState.Loading)
    val uiState: StateFlow<UiState<SubscriptionListResponse>> get() = _uiState

    private val _numberState = MutableStateFlow("")
    val numberState: StateFlow<String> get() = _numberState

    private val _nameState = MutableStateFlow("")
    val nameState: StateFlow<String> get() = _nameState

    private val _emailState = MutableStateFlow("")
    val emailState: StateFlow<String> get() = _emailState

    init {
        viewModelScope.launch {
            _numberState.value = prefManager.getUserMobileNumber.first()!!
            _nameState.value = prefManager.getUserFullName.first()!!
            _emailState.value = prefManager.getEmail.first()!!
        }
    }
}
