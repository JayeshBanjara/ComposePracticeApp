package com.example.demoappcompose.ui.print_settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demoappcompose.data.PreferencesManager
import com.example.demoappcompose.data.requests.ConfigRequest
import com.example.demoappcompose.data.requests.GeneratePaperRequest
import com.example.demoappcompose.data.requests.SectionNew
import com.example.demoappcompose.data.responses.GeneratePaperResponse
import com.example.demoappcompose.data.responses.config.ConfigResponse
import com.example.demoappcompose.network.ApiException
import com.example.demoappcompose.network.UnAuthorisedException
import com.example.demoappcompose.repository.AppRepository
import com.example.demoappcompose.repository.UserRepository
import com.example.demoappcompose.utility.Constants
import com.example.demoappcompose.utility.UiState
import com.example.demoappcompose.utility.printLongJson
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrintSettingsViewModel @Inject constructor(
    private val prefManager: PreferencesManager,
    private val appRepository: AppRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<ConfigResponse>>(UiState.Loading)
    val uiState: StateFlow<UiState<ConfigResponse>> get() = _uiState

    private val _generatePaperState =
        MutableStateFlow<UiState<GeneratePaperResponse>>(UiState.Empty)
    val generatePaperState: StateFlow<UiState<GeneratePaperResponse>> get() = _generatePaperState

    lateinit var roleId: String

    init {
        viewModelScope.launch {
            roleId = prefManager.getRoleId.first() ?: "3"
            getConfig()
        }
    }

    private fun getGenTypeId(genType: String): String {
        var str = "2"
        when(genType) {
            "PDF Only Exam Paper" -> str = "0"
            "PDF Exam Paper & Solution with only Answer" -> str = "1"
            "Material" -> str = "2"
        }
        return str
    }

    private suspend fun getConfig() = viewModelScope.launch {

        val request = ConfigRequest(
            deviceType = Constants.DEVICE_TYPE,
            configType = "1"
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
            prefManager.clearData()
            _uiState.value = UiState.UnAuthorised(e.message)
        } catch (e: Exception) {
            _uiState.value = UiState.Error(e.message)
        }
    }

    suspend fun generatePaper(
        classId: String,
        className: String,
        subjectId: String,
        subjectName: String,
        mediumId: String,
        chapterNumber: String,
        instituteName: String,
        examDate: String,
        examTime: String,
        examMarks: String,
        examName: String,
        isPageFooter1: String,
        pageFooter: String,
        isWaterMark1: String,
        waterMark: String,
        messageForEndOfPaper: String,
        pageBorder: String,
        fontSize: String,
        generationType: String,
        sectionList: List<SectionNew>
    ) = viewModelScope.launch {

        _generatePaperState.value = UiState.Loading

        val userId = prefManager.getUserId.first()!!
        val token = prefManager.getToken.first()!!

        val headers = HashMap<String, String>()
        headers[Constants.TOKEN_KEY] = token

        val request = GeneratePaperRequest(
            deviceType = Constants.DEVICE_TYPE,
            userId = userId,
            classId = classId,
            className = className,
            subjectId = subjectId,
            subjectName = subjectName,
            mediumId = mediumId,
            chapterNumber = chapterNumber,
            instituteName = instituteName,
            fontSize = fontSize,
            generationType = getGenTypeId(genType = generationType),
            examDate = examDate,
            examTime = examTime,
            examMarks = examMarks,
            examName = examName,
            isPageFooter = isPageFooter1,
            isWaterMark = isWaterMark1,
            waterMark = waterMark,
            messageForEndOfPaper = messageForEndOfPaper,
            pageBorder = pageBorder,
            pageFooter = pageFooter,
            sectionList = sectionList
        )

        try {

            printLongJson(Gson().toJson(request), "TAGG")

            val response = userRepository.generatePaper(headerMap = headers, request = request)
            if (response.statusCode == 200) {
                _generatePaperState.value = UiState.Success(response)
            } else {
                _generatePaperState.value = UiState.Error(response.message)
            }
        } catch (e: ApiException) {
            _generatePaperState.value = UiState.Error(e.message)
        } catch (e: UnAuthorisedException) {
            prefManager.clearData()
            _generatePaperState.value = UiState.UnAuthorised(e.message)
        } catch (e: Exception) {
            _generatePaperState.value = UiState.Error(e.message)
        }
    }

}
