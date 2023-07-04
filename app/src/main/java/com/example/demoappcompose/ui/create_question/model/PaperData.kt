package com.example.demoappcompose.ui.create_question.model

import com.example.demoappcompose.data.responses.questions.HeadingData

data class PaperData(
    var isSectionWise: Boolean,
    var isAddNewInSameSection: Boolean,
    var lastSectionName: String,
    var sectionList: List<Section>
)

data class Section(
    var hasSectionName: Boolean,
    var sectionName: String,
    val headingList: List<HeadingData>,
    var selectedHeading: HeadingData?,
    var marks: String?
)
