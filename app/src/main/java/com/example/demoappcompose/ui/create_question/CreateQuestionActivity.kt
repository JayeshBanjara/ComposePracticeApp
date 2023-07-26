package com.example.demoappcompose.ui.create_question

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.demoappcompose.R

class CreateQuestionActivity : AppCompatActivity() {

    private lateinit var classId: String
    private lateinit var className: String
    private lateinit var subjectId: String
    private lateinit var subjectName: String
    private lateinit var mediumType: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_question)

        classId = intent.getStringExtra("classId") ?: ""
        className = intent.getStringExtra("className") ?: ""
        subjectId = intent.getStringExtra("subjectId") ?: ""
        subjectName = intent.getStringExtra("subjectName") ?: ""
        mediumType = intent.getStringExtra("mediumType") ?: ""


    }
}