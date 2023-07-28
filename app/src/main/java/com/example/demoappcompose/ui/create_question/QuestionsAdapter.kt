package com.example.demoappcompose.ui.create_question

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.demoappcompose.R
import com.example.demoappcompose.data.responses.question_list.QuestionData

class QuestionsAdapter(
    private val questionList: MutableList<QuestionData>
) : RecyclerView.Adapter<QuestionsAdapter.QuestionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_question, parent, false)
        return QuestionViewHolder(view)
    }

    override fun getItemCount(): Int = questionList.size

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val questionData = questionList[position]
        holder.itemNameTextView.text = "${position + 1}"


        holder.imgBtnDeleteSection.setOnClickListener {
            questionList.removeAt(holder.adapterPosition)
            notifyItemRemoved(holder.adapterPosition)
        }
    }

    class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemNameTextView: TextView = itemView.findViewById(R.id.txt_question)
        val imgBtnDeleteSection: ImageButton = itemView.findViewById(R.id.imgBtnRemove)
    }
}