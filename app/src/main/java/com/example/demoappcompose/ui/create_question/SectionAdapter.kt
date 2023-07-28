package com.example.demoappcompose.ui.create_question

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.demoappcompose.R
import com.example.demoappcompose.ui.create_question.SectionAdapter.SectionViewHolder
import com.example.demoappcompose.ui.create_question.model.Section
import com.xiaofeng.flowlayoutmanager.FlowLayoutManager

class SectionAdapter(
    private val context: Context,
    private val sectionList: MutableList<Section>,
    private val classId: String,
    private val subjectId: String,
    private val subjectName: String,
    private val clickListener: SectionClickListener
) : RecyclerView.Adapter<SectionViewHolder>() {

    private var showSection = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_section, parent, false)
        return SectionViewHolder(view)
    }

    override fun getItemCount(): Int = sectionList.size

    override fun onBindViewHolder(holder: SectionViewHolder, position: Int) {
        val section = sectionList[position]
        holder.itemNameTextView.text = "Section ${section.sectionName}"

        if (showSection) {
            holder.itemNameTextView.visibility = View.VISIBLE
        } else {
            holder.itemNameTextView.visibility = View.GONE
        }

        val arrayAdapter = ArrayAdapter(context, R.layout.dropdown_item, section.headingList)
        holder.autocompleteTV.setAdapter(arrayAdapter)
        holder.autocompleteTV.setText(section.selectedHeading?.headingName, false)
        holder.autocompleteTV.setOnItemClickListener { adapterView, view, pos, l ->
            section.selectedHeading = section.headingList[pos]
            holder.autocompleteTV.setText(section.headingList[pos].headingName)
        }

        holder.imgBtnAddQuestion.setOnClickListener {

            clickListener.onAddClick(section = section, pos = position)
        }

        val flowLayoutManager = FlowLayoutManager()
        flowLayoutManager.isAutoMeasureEnabled = true
        holder.rcvQuestions.layoutManager = flowLayoutManager
        holder.rcvQuestions.adapter = QuestionsAdapter(section.questions!!)

        holder.imgBtnDeleteSection.setOnClickListener {
            sectionList.removeAt(holder.adapterPosition)
            notifyItemRemoved(holder.adapterPosition)
        }
    }

    fun update(isShowSection: Boolean) {
        showSection = isShowSection
        notifyDataSetChanged()
    }

    class SectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemNameTextView: TextView = itemView.findViewById(R.id.txt_section)
        val autocompleteTV: AutoCompleteTextView = itemView.findViewById(R.id.autoCompleteTextView)
        val edtMarks: EditText = itemView.findViewById(R.id.edt_marks)
        val imgBtnAddQuestion: ImageButton = itemView.findViewById(R.id.imgBtn_add_question)
        val imgBtnDeleteSection: ImageButton = itemView.findViewById(R.id.imgBtn_delete_section)
        val rcvQuestions: RecyclerView = itemView.findViewById(R.id.rcv_questions)
    }

    interface SectionClickListener {
        fun onAddClick(section: Section, pos: Int)
    }
}