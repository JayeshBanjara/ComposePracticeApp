package com.example.demoappcompose.ui.create_question

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demoappcompose.data.PreferencesManager
import com.example.demoappcompose.data.requests.HeadingListRequest
import com.example.demoappcompose.data.responses.questions.HeadingData
import com.example.demoappcompose.data.responses.questions.HeadingListResponse
import com.example.demoappcompose.network.ApiException
import com.example.demoappcompose.network.UnAuthorisedException
import com.example.demoappcompose.repository.UserRepository
import com.example.demoappcompose.ui.create_question.model.Section
import com.example.demoappcompose.utility.Constants
import com.example.demoappcompose.utility.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateQuestionViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val prefManager: PreferencesManager
) : ViewModel() {

    private val _getHeadingState = MutableStateFlow<UiState<HeadingListResponse>>(UiState.Empty)
    val getHeadingState: StateFlow<UiState<HeadingListResponse>> get() = _getHeadingState

    val sectionWiseCheckedState = mutableStateOf(false)
    val newQueSameSectionCheckedState = mutableStateOf(false)
    val lastSectionName = mutableIntStateOf(65)
    val sectionList = mutableStateListOf<Section>()
    var headingList = mutableListOf<HeadingData>()
    val deletedSections = mutableListOf<Int>()

    init {
        val section = Section(
            hasSectionName = true,
            sectionName = lastSectionName.value.toChar().toString(),
            headingList = headingList,
            selectedHeading = null,
            marks = null
        )

        sectionList.add(section)
    }

    suspend fun getHeadingList(classId: String, subjectId: String) = viewModelScope.launch {

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
            val response = userRepository.getHeadingList(headerMap = headers, request = request)
            if (response.statusCode == 200) {
                _getHeadingState.value = UiState.Success(response)
            } else {
                _getHeadingState.value = UiState.Error(response.message)
            }
        } catch (e: UnAuthorisedException) {
            prefManager.clearData()
            _getHeadingState.value = UiState.UnAuthorised(e.message)
        } catch (e: ApiException) {
            _getHeadingState.value = UiState.Error(e.message)
        } catch (e: Exception) {
            _getHeadingState.value = UiState.Error(e.message)
        }
    }

    fun getSectionName(): String {

        var sectionName = ""

        if (sectionWiseCheckedState.value and newQueSameSectionCheckedState.value.not()) {

            if (deletedSections.isNotEmpty()) {
                sectionName = (deletedSections.min()).toChar().toString()
                val isExist = sectionList.find { it.sectionName == sectionName }
                if(isExist != null) {
                    sectionName = (lastSectionName.value + 1).toChar().toString()
                }
            } else {
                sectionName = (lastSectionName.value + 1).toChar().toString()
            }
        } else {
            sectionName = if (deletedSections.isNotEmpty()) {
                (deletedSections.min()).toChar().toString()
            } else {
                lastSectionName.value.toChar().toString()
            }
        }

        return sectionName
    }
}
