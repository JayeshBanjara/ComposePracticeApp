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
import com.example.demoappcompose.ui.create_question.model.DummyRequest
import com.example.demoappcompose.ui.create_question.model.PaperData
import com.example.demoappcompose.ui.create_question.model.QuestionsArr
import com.example.demoappcompose.ui.create_question.model.Section
import com.example.demoappcompose.utility.Constants
import com.example.demoappcompose.utility.UiState
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateQuestionViewModel @Inject constructor(
    private val userRepository: UserRepository, private val prefManager: PreferencesManager
) : ViewModel() {

    private val _getHeadingState = MutableStateFlow<UiState<HeadingListResponse>>(UiState.Empty)
    val getHeadingState: StateFlow<UiState<HeadingListResponse>> get() = _getHeadingState

    val sectionWiseCheckedState = mutableStateOf(false)
    val newQueSameSectionCheckedState = mutableStateOf(false)
    val lastSectionName = mutableIntStateOf(65)
    val sectionList = mutableStateListOf<Section>()
    var headingList = mutableListOf<HeadingData>()
    val deletedSections = mutableListOf<Int>()
    var activeSection = 0

    init {
        val section = Section(
            hasSectionName = true,
            sectionName = lastSectionName.value.toChar().toString(),
            headingList = headingList,
            selectedHeading = null,
            marks = null
        )

        sectionList.add(section)
        activeSection = section.sectionId
    }

    fun isListReady(): Boolean {

        //Find of any question has null heading, if found we will return
        val heading = sectionList.find { it.selectedHeading == null }
        if (heading != null) return false

        //Find of any question has null marks, if found we will return
        val marks = sectionList.find { it.marks.isNullOrEmpty() }
        if (marks != null) return false

        //Find of any question has null questions, if found we will return
        val questions = sectionList.find { it.questions.isNullOrEmpty() }
        if (questions != null) return false

        return true
    }

    fun prepareRequest(
        classId: String, className: String, subjectId: String, subjectName: String, mediumId: String
    ): String {

        val mainList = mutableListOf<DummyRequest>()

        sectionList.forEach {

            val qList = mutableListOf<QuestionsArr>()

            it.questions?.forEach { it2 ->
                val questionArr = QuestionsArr(
                    qId = it2.qId, question = it2.question, options = it2.options
                )

                qList.add(questionArr)
            }

            val dummyRequest = DummyRequest(
                isSectionName = it.hasSectionName,
                sectionName = it.sectionName,
                heading = it.selectedHeading?.headingName!!,
                marks = it.marks?.toInt()!!,
                questionsArr = qList
            )

            mainList.add(dummyRequest)

        }

        val paperData = PaperData(
            sectionList = sectionList,
            classId = classId,
            className = className,
            subjectId = subjectId,
            subjectName = subjectName,
            mediumId = mediumId
        )

        return Gson().toJson(paperData)
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
                sectionList[0].selectedHeading = response.headingListData.headingList[0]
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
                if (isExist != null) {
                    sectionName = (lastSectionName.value + 1).toChar().toString()
                }
            } else {
                sectionName = (sectionList.last().sectionName[0].code + 1).toChar().toString()
            }
        } else {
            sectionName = if (deletedSections.isNotEmpty()) {
                (deletedSections.min()).toChar().toString()
            } else {
                sectionList.last().sectionName
            }
        }

        return sectionName
    }
}
