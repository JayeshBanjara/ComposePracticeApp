package com.example.demoappcompose.ui.paper_history

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.demoappcompose.R
import com.example.demoappcompose.data.responses.paper_history.PaperHistory
import com.example.demoappcompose.ui.HorizontalSpacer
import com.example.demoappcompose.ui.VerticalSpacer
import com.example.demoappcompose.ui.theme.Blue
import com.example.demoappcompose.ui.theme.TitleColor
import com.example.demoappcompose.utility.openUrl

@Composable
fun PaperHistoryItem(paperHistory: PaperHistory, context: Context) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 5.dp
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentHeight()
                ) {
                    VerticalSpacer(size = 5)
                    Text(
                        text = paperHistory.instituteName,
                        style = TextStyle(
                            color = TitleColor,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.W500,
                            fontFamily = FontFamily(Font(R.font.quicksand_bold))
                        )
                    )
                    VerticalSpacer(size = 7)
                    Text(
                        text = "Standard: ${paperHistory.className} (${paperHistory.mediumName}) - ${paperHistory.subjectName}",
                        style = TextStyle(
                            color = Blue,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.W500,
                            fontFamily = FontFamily(Font(R.font.quicksand_bold))
                        )
                    )
                    VerticalSpacer(size = 5)
                }
                HorizontalSpacer(size = 10)
                IconButton(onClick = {
                    context.openUrl(paperHistory.fileUrl)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_print),
                        contentDescription = null,
                        tint = Blue,
                        modifier = Modifier
                            .size(40.dp)
                    )
                }
            }
        }
        VerticalSpacer(size = 15)
    }
}