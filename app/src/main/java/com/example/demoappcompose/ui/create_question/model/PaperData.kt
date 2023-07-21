package com.example.demoappcompose.ui.create_question.model

import com.example.demoappcompose.data.responses.question_list.QuestionData
import com.example.demoappcompose.data.responses.questions.HeadingData
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.io.Serializable
import kotlin.random.Random

data class PaperData(
    var sectionList: List<Section>,
    var classId: String,
    var className: String,
    var subjectId: String,
    var subjectName: String,
    var mediumId: String
)

data class Section(
    var sectionId: Int = Random.nextInt(10000),
    var hasSectionName: Boolean,
    var sectionName: String,
    val headingList: List<HeadingData>,
    var selectedHeading: HeadingData?,
    var marks: String?,
    var questions: MutableList<QuestionData>? = null
)

/*
object Serializer {
    */
/*private val moshi = Moshi.Builder().build()
    private val adapter: JsonAdapter<Section> =
        moshi.adapter(Types.newParameterizedType(Section::class.java))*//*


    private val moshi = Moshi.Builder().add(com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory()).build()
    private val adapter: JsonAdapter<Section> =
        moshi.adapter(Section::class.java)

    fun serialize(data: Section): String {
        return adapter.toJson(data)
    }

    fun deserialize(json: String): Section? {
        return adapter.fromJson(json)
    }
}*/
