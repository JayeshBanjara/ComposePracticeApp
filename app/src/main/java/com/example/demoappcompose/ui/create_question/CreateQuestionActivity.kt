package com.example.demoappcompose.ui.create_question

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.demoappcompose.R
import com.example.demoappcompose.data.responses.question_list.QuestionData
import com.example.demoappcompose.data.responses.questions.HeadingData
import com.example.demoappcompose.data.responses.questions.HeadingListResponse
import com.example.demoappcompose.ui.create_question.chapterList.ChapterListActivity
import com.example.demoappcompose.ui.create_question.model.Section
import com.example.demoappcompose.ui.print_settings.PrintSettingsActivity
import com.example.demoappcompose.utility.getSerializable
import com.example.demoappcompose.utility.toast
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateQuestionActivity : AppCompatActivity(), CreateQuestionInterface,
    SectionAdapter.SectionClickListener {

    private lateinit var classId: String
    private lateinit var className: String
    private lateinit var subjectId: String
    private lateinit var subjectName: String
    private lateinit var mediumId: String

    private lateinit var progressBar: ProgressBar
    private lateinit var imgBack: ImageView
    private lateinit var imgPrint: ImageView
    private lateinit var cbIsSection: CheckBox
    private lateinit var cbInSameSection: CheckBox
    private lateinit var cbInSameSectionLayout: LinearLayout
    private lateinit var rcvSection: RecyclerView
    private lateinit var sectionAdapter: SectionAdapter
    private lateinit var fabAddSection: ExtendedFloatingActionButton

    private var lastSectionName = 65
    private var sectionList = mutableListOf<Section>()
    private var headingList = mutableListOf<HeadingData>()
    private val deletedSections = mutableListOf<Int>()

    private var isSectionWise = false
    private var isInSameSection = false

    private val viewModel by viewModels<CreateQuestionViewModel>()

    private var currentPos = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_create_question)

        viewModel.createQueInterface = this

        classId = intent.getStringExtra("classId") ?: ""
        className = intent.getStringExtra("className") ?: ""
        subjectId = intent.getStringExtra("subjectId") ?: ""
        subjectName = intent.getStringExtra("subjectName") ?: ""
        mediumId = intent.getStringExtra("mediumId") ?: ""

        progressBar = findViewById(R.id.progress_bar)
        imgBack = findViewById(R.id.img_back)
        imgPrint = findViewById(R.id.img_print)
        cbIsSection = findViewById(R.id.cb1)
        cbInSameSection = findViewById(R.id.cb2)
        cbInSameSectionLayout = findViewById(R.id.layout_cb2)
        rcvSection = findViewById(R.id.rcv_section)
        fabAddSection = findViewById(R.id.fab_add_section)

        fabAddSection.setOnClickListener {

            var questions = mutableListOf<QuestionData>()

            val newSection = Section(
                hasSectionName = cbIsSection.isChecked,
                sectionName = getSectionName(),
                headingList = headingList,
                selectedHeading = headingList[0],
                marks = "",
                questions = questions
            )

            sectionList.add(newSection)
            sectionList.sortBy { it.sectionName[0].code }
            lastSectionName = newSection.sectionName[0].code

            sectionAdapter.notifyDataSetChanged()
        }

        cbIsSection.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                isSectionWise = true
                cbInSameSectionLayout.visibility = View.VISIBLE
            } else {
                isSectionWise = false
                cbInSameSectionLayout.visibility = View.GONE
            }

            sectionAdapter.update(isShowSection = isSectionWise)
        }

        cbInSameSection.setOnCheckedChangeListener { _, isChecked ->
            isInSameSection = isChecked
        }

        imgBack.setOnClickListener {
            finish()
        }

        lifecycleScope.launch {
            viewModel.getHeadingList(classId = classId, subjectId = subjectId)
        }

        imgPrint.setOnClickListener {

            //Find of any question has null heading, if found we will return
            val heading = sectionList.find { it.selectedHeading == null }

            //Find of any question has null marks, if found we will return
            val marks = sectionList.find { it.marks.isNullOrEmpty() }

            //Find of any question has null questions, if found we will return
            val questions = sectionList.find { it.questions.isNullOrEmpty() }

            if (heading != null) {
                toast("Please add Heading")
            } else if (marks != null) {
                toast("Please add Marks")
            } else if (questions != null) {
                toast("Please add Questions")
            } else {

                val paperData = viewModel.prepareRequest(
                    classId = classId,
                    className = className,
                    subjectId = subjectId,
                    subjectName = subjectName,
                    mediumId = mediumId,
                    sectionList
                )
                //navController.navigate(Screens.PrintSettings.withArgs(paperDataStr))
                val i = Intent(this, PrintSettingsActivity::class.java)
                i.putExtra("paperData", paperData)
                startActivity(i)
            }
        }
    }

    private fun getSectionName(): String {

        var sectionName = ""

        if (cbIsSection.isChecked and cbInSameSection.isChecked.not()) {

            if (deletedSections.isNotEmpty()) {
                sectionName = (deletedSections.min()).toChar().toString()
                val isExist = sectionList.find { it.sectionName == sectionName }
                if (isExist != null) {
                    sectionName = (lastSectionName + 1).toChar().toString()
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

    override fun onSuccess(headingListResponse: HeadingListResponse) {

        progressBar.visibility = View.GONE

        var questions = mutableListOf<QuestionData>()

        headingList.clear()
        headingList.addAll(headingListResponse.headingListData.headingList)
        val section = Section(
            hasSectionName = true,
            sectionName = lastSectionName.toChar().toString(),
            headingList = headingList,
            selectedHeading = null,
            marks = null,
            questions = questions
        )
        sectionList.add(section)

        sectionAdapter = SectionAdapter(
            context = this,
            sectionList = sectionList,
            classId = classId,
            subjectId = subjectId,
            subjectName = subjectName,
            clickListener = this
        )

        rcvSection.adapter = sectionAdapter

        sectionList[0].selectedHeading = headingListResponse.headingListData.headingList[0]
    }

    override fun onFailure(message: String) {
        progressBar.visibility = View.GONE
        toast(message)
    }

    override fun onStarted() {
        progressBar.visibility = View.VISIBLE
    }

    override fun onSessionExpire(message: String) {
        progressBar.visibility = View.GONE
        toast(message)
    }

    private var chapterActivityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val updatedSection = result.data?.getSerializable("section", Section::class.java)
                sectionList.find { it.sectionId == updatedSection?.sectionId }?.questions = updatedSection?.questions
                sectionAdapter.notifyItemChanged(currentPos)
            }
        }

    override fun onAddClick(section: Section, pos: Int) {
        currentPos = pos
        val i = Intent(this, ChapterListActivity::class.java)
        i.apply {
            putExtra("section", section)
            putExtra("classId", classId)
            putExtra("subjectId", subjectId)
            putExtra("subjectName", subjectName)
        }
        chapterActivityLauncher.launch(i)
    }
}