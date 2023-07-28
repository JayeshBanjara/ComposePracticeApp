package com.example.demoappcompose.ui.create_question

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.recyclerview.widget.RecyclerView
import com.example.demoappcompose.R
import com.example.demoappcompose.data.responses.questions.HeadingData
import com.example.demoappcompose.ui.create_question.chapterList.ChapterListActivity
import com.example.demoappcompose.ui.create_question.model.Section

class CreateQuestionActivity : AppCompatActivity() {

    private lateinit var classId: String
    private lateinit var className: String
    private lateinit var subjectId: String
    private lateinit var subjectName: String
    private lateinit var mediumType: String

    private lateinit var imgBack: ImageView
    private lateinit var imgPrint: ImageView
    private lateinit var cbIsSection: CheckBox
    private lateinit var cbInSameSection: CheckBox
    private lateinit var cbInSameSectionLayout: LinearLayout
    private lateinit var rcvSection: RecyclerView

    private val lastSectionName = 65
    private var sectionList = mutableListOf<Section>()
    private var headingList = mutableListOf<HeadingData>()
    private val deletedSections = mutableListOf<Int>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_create_question)

        classId = intent.getStringExtra("classId") ?: ""
        className = intent.getStringExtra("className") ?: ""
        subjectId = intent.getStringExtra("subjectId") ?: ""
        subjectName = intent.getStringExtra("subjectName") ?: ""
        mediumType = intent.getStringExtra("mediumType") ?: ""

        imgBack = findViewById(R.id.img_back)
        imgPrint = findViewById(R.id.img_print)
        cbIsSection = findViewById(R.id.cb1)
        cbInSameSection = findViewById(R.id.cb2)
        cbInSameSectionLayout = findViewById(R.id.layout_cb2)
        rcvSection = findViewById(R.id.rcv_section)

        val section = Section(
            hasSectionName = true,
            sectionName = lastSectionName.toChar().toString(),
            headingList = headingList,
            selectedHeading = null,
            marks = null
        )

        sectionList.add(section)

        rcvSection.adapter = SectionAdapter(sectionList)

        cbIsSection.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
                cbInSameSectionLayout.visibility = View.VISIBLE
            } else {
                cbInSameSectionLayout.visibility = View.GONE
            }
        }

        cbIsSection.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {

            } else {

            }
        }



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