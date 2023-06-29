package com.example.demoappcompose.ui.dashboard.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demoappcompose.data.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val prefManager: PreferencesManager
) : ViewModel() {

    fun clearPreference() = viewModelScope.launch {
        prefManager.clearData()
    }

}
