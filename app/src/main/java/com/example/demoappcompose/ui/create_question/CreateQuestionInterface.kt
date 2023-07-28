package com.example.demoappcompose.ui.create_question

import com.example.demoappcompose.data.responses.questions.HeadingListResponse

interface CreateQuestionInterface {
    fun onSuccess(headingListResponse: HeadingListResponse)
    fun onFailure(message: String)
    fun onStarted()
    fun onSessionExpire(message: String)
}