package com.example.demoappcompose.ui.subject.components

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.demoappcompose.R
import com.example.demoappcompose.data.responses.subjects.Subject
import com.example.demoappcompose.ui.VerticalSpacer
import com.example.demoappcompose.ui.create_question.CreateQuestionActivity
import com.example.demoappcompose.ui.navigation.Screens
import com.example.demoappcompose.ui.theme.Blue
import com.example.demoappcompose.ui.theme.LightBlue
import com.example.demoappcompose.ui.theme.TitleColor

@Composable
fun SubjectItem(context: Context, subject: Subject, navController: NavController) {
    Box(
        modifier = Modifier
            .padding(10.dp)
            .height(140.dp)
            .fillMaxWidth()
            .border(
                BorderStroke(width = 1.dp, color = Blue),
                shape = RoundedCornerShape(10.dp)
            )
            .background(color = LightBlue, shape = RoundedCornerShape(10.dp))
            .clickable {

                val intent = Intent(context, CreateQuestionActivity::class.java)
                intent.putExtra("classId", subject.classId.toString())
                intent.putExtra("className", subject.className)
                intent.putExtra("subjectId", subject.sId.toString())
                intent.putExtra("subjectName", subject.subjectName)
                intent.putExtra("mediumType", subject.mediumType.toString())

                context.startActivity(intent)

                /*navController.navigate(
                    Screens.CreateQuestion.withArgs(
                        subject.classId.toString(),
                        subject.className,
                        subject.sId.toString(),
                        subject.subjectName,
                        subject.mediumType.toString()
                    )
                )*/
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            VerticalSpacer(size = 5)
            Image(
                painter = painterResource(id = R.drawable.books),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
            VerticalSpacer(size = 5)
            Text(
                text = subject.subjectName,
                style = TextStyle(
                    color = TitleColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W500,
                    fontFamily = FontFamily(Font(R.font.quicksand_bold))
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}