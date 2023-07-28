package com.example.demoappcompose.ui.create_question

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.demoappcompose.R
import com.example.demoappcompose.ui.create_question.SectionAdapter.SectionViewHolder
import com.example.demoappcompose.ui.create_question.model.Section

class SectionAdapter(private val sectionList: List<Section>): RecyclerView.Adapter<SectionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_section, parent, false)
        return SectionViewHolder(view)
    }

    override fun getItemCount(): Int = sectionList.size

    override fun onBindViewHolder(holder: SectionViewHolder, position: Int) {
        val section = sectionList[position]
        holder.itemNameTextView.text = "Section ${section.sectionName}"
    }

    class SectionViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val itemNameTextView: TextView = itemView.findViewById(R.id.txt_section)
    }
}