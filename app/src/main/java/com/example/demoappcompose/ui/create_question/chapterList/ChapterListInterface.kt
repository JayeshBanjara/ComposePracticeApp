package com.example.demoappcompose.ui.create_question.chapterList

import com.example.demoappcompose.data.responses.chapter_list.ChapterListResponse

interface ChapterListInterface {
    fun onSuccess(chapterListResponse: ChapterListResponse)
    fun onFailure(message: String)
    fun onStarted()
    fun onSessionExpire(message: String)
}