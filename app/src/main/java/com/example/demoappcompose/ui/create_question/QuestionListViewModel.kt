package com.example.demoappcompose.ui.create_question

import android.os.Build
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demoappcompose.data.PreferencesManager
import com.example.demoappcompose.data.requests.QuestionListRequest
import com.example.demoappcompose.data.responses.question_list.QuestionData
import com.example.demoappcompose.data.responses.question_list.QuestionListResponse
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
class QuestionListViewModel @Inject constructor(
    private val userRepository: UserRepository, private val prefManager: PreferencesManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<QuestionListResponse>>(UiState.Empty)
    val uiState: StateFlow<UiState<QuestionListResponse>> get() = _uiState

    var classId: String = ""
    var subjectId: String = ""
    var chapterId: String = ""
    var questionTypeId: String = "1"
    val questionList = mutableStateListOf<QuestionData>()
    val selectedQuestions = mutableListOf<QuestionData>()

    private val _selectedQueCountState = MutableStateFlow(0)
    val selectedQueCount: StateFlow<Int> get() = _selectedQueCountState

    suspend fun getQuestionList() = viewModelScope.launch {

        val userId = prefManager.getUserId.first()!!
        val token = prefManager.getToken.first()!!

        val headers = HashMap<String, String>()
        headers[Constants.TOKEN_KEY] = token

        val request = QuestionListRequest(
            deviceType = Constants.DEVICE_TYPE,
            userId = userId,
            classId = classId,
            subjectId = subjectId,
            chapterId = chapterId,
            questionTypeId = questionTypeId
        )

        try {
            val response = userRepository.getQuestionList(headerMap = headers, request = request)
            if (response.statusCode == 200) {
                questionList.clear()
                questionList.addAll(response.questionListData.questionList)
                selectedQuestions.forEach { selectedQuestion ->
                    questionList.forEach { question ->
                        if (question.qId == selectedQuestion.qId) {
                            question.isSelected = true
                        }
                    }
                }
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


    fun removeQuestionSelection(index: Int, questionData: QuestionData) {
        selectedQuestions.remove(questionData)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            selectedQuestions.removeIf { it.qId == questionData.qId }
        } else {
            val x =
                selectedQuestions.find { it.qId == questionData.qId }
            selectedQuestions.remove(x)
        }
        questionList[index] = questionData.copy(isSelected = false)
        _selectedQueCountState.value = selectedQuestions.size
    }

    fun setQuestionSelection(index: Int, questionData: QuestionData) {
        selectedQuestions.add(questionData)
        questionList[index] = questionData.copy(isSelected = true)
        _selectedQueCountState.value = selectedQuestions.size
    }
}
