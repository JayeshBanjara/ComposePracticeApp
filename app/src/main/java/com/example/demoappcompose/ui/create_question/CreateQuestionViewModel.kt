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
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.net.URLEncoder
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
    var sectionList = mutableStateListOf<Section>()
    var headingList = mutableListOf<HeadingData>()
    val deletedSections = mutableListOf<Int>()
    var activeSection = 0

    var createQueInterface: CreateQuestionInterface? = null

    fun removeQuestion(sectionIndex: Int, questionIndex: Int) {
        sectionList[sectionIndex].questions?.removeAt(questionIndex)
        sectionList[sectionIndex] = sectionList[sectionIndex]
    }

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

//        val x = Json.encodeToString(paperData)

        val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val adapter: JsonAdapter<PaperData> = moshi.adapter(PaperData::class.java)
        val jsonString = adapter.toJson(paperData)
        val encodedJsonString = URLEncoder.encode(jsonString, "utf-8")

        //return Gson().toJson(x)
        return encodedJsonString
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
                //sectionList[0].selectedHeading = response.headingListData.headingList[0]
                //_getHeadingState.value = UiState.Success(response)
                createQueInterface?.onSuccess(headingListResponse = response)
            } else {
                //_getHeadingState.value = UiState.Error(response.message)
                createQueInterface?.onFailure(response.message)
            }
        } catch (e: UnAuthorisedException) {
            prefManager.clearData()
            //_getHeadingState.value = UiState.UnAuthorised(e.message)
            createQueInterface?.onFailure(e.message.toString())
        } catch (e: ApiException) {
           // _getHeadingState.value = UiState.Error(e.message)
            createQueInterface?.onFailure(e.message.toString())
        } catch (e: Exception) {
           // _getHeadingState.value = UiState.Error(e.message)
            createQueInterface?.onFailure(e.message.toString())
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
