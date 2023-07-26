package com.example.demoappcompose.ui.create_question

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.demoappcompose.R
import com.example.demoappcompose.ui.create_question.chapterList.ChapterListActivity

class CreateQuestionActivity : AppCompatActivity() {

    private lateinit var classId: String
    private lateinit var className: String
    private lateinit var subjectId: String
    private lateinit var subjectName: String
    private lateinit var mediumType: String

    private lateinit var imgBack: ImageView
    private lateinit var imgPrint: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_question)

        classId = intent.getStringExtra("classId") ?: ""
        className = intent.getStringExtra("className") ?: ""
        subjectId = intent.getStringExtra("subjectId") ?: ""
        subjectName = intent.getStringExtra("subjectName") ?: ""
        mediumType = intent.getStringExtra("mediumType") ?: ""

        imgBack = findViewById(R.id.img_back)
        imgPrint = findViewById(R.id.img_print)

        imgBack.setOnClickListener {
            finish()
        }

        imgPrint.setOnClickListener {
            val i = Intent(this, ChapterListActivity::class.java)
            i.apply {
                putExtra("classId", classId)
                putExtra("subjectId", subjectId)
                putExtra("subjectName", subjectName)
            }
            startActivity(i)
        }
    }
}