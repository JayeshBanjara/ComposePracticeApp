package com.example.demoappcompose.ui.create_question.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.demoappcompose.R
import com.example.demoappcompose.data.responses.question_list.QuestionData
import com.example.demoappcompose.ui.HorizontalSpacer
import com.example.demoappcompose.ui.theme.AppDarkGreen
import com.example.demoappcompose.ui.theme.AppLightGreen
import com.example.demoappcompose.ui.theme.Blue
import com.example.demoappcompose.ui.theme.HintColor
import com.example.demoappcompose.ui.theme.WhiteText
import com.example.demoappcompose.ui.theme.WhiteText2

@Composable
fun QuestionItem(
    questionData: QuestionData,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Row(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = if (isSelected) {
                    AppDarkGreen
                } else {
                    Blue
                },
                shape = RectangleShape
            )
            .background(
                color = if (isSelected) {
                    AppLightGreen
                } else {
                    if(questionData.fromBook == 1) WhiteText else WhiteText2
                }
            )
            .padding(10.dp)
            .clickable {
                onSelect()
            }
    ) {

        Checkbox(
            checked = isSelected,
            onCheckedChange = {
                onSelect()
            })

        HorizontalSpacer(size = 8)

        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = questionData.question,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.W500,
                    fontFamily = FontFamily(Font(R.font.quicksand_medium))
                )
            )

            if(questionData.options != null) {

                Text(
                    text = questionData.options,
                    style = TextStyle(
                        color = HintColor,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.W500,
                        fontFamily = FontFamily(Font(R.font.quicksand_medium))
                    )
                )

                /*Column(modifier = Modifier.fillMaxWidth()) {
                    VerticalSpacer(size = 8)

                    Text(
                        text = "A. Sucks beauty",
                        style = TextStyle(
                            color = HintColor,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.W500,
                            fontFamily = FontFamily(Font(R.font.quicksand_medium))
                        )
                    )
                    VerticalSpacer(size = 2)

                    Text(
                        text = "B. reflects the beauty",
                        style = TextStyle(
                            color = HintColor,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.W500,
                            fontFamily = FontFamily(Font(R.font.quicksand_medium))
                        )
                    )
                    VerticalSpacer(size = 2)

                    Text(
                        text = "C. welcomes beauty",
                        style = TextStyle(
                            color = HintColor,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.W500,
                            fontFamily = FontFamily(Font(R.font.quicksand_medium))
                        )
                    )
                    VerticalSpacer(size = 2)

                    Text(
                        text = "D. rejects the beauty",
                        style = TextStyle(
                            color = HintColor,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.W500,
                            fontFamily = FontFamily(Font(R.font.quicksand_medium))
                        )
                    )

                    VerticalSpacer(size = 2)
                }*/
            }
        }
    }
}