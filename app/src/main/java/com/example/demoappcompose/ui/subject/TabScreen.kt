package com.example.demoappcompose.ui.subject

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.demoappcompose.R
import com.example.demoappcompose.ui.theme.Blue
import com.example.demoappcompose.ui.theme.HintColor

@Composable
fun TabScreen(
    navController: NavController,
    subjectsViewModel: SubjectsViewModel,
    classId: String
) {
    var tabIndex by remember { mutableIntStateOf(0) }

    val tabs = listOf("સાયન્સ", "કૉમેર્સ", "આર્ટસ")

    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(selectedTabIndex = tabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(text = {
                    Text(
                        text = title,
                        style = TextStyle(
                            color = if (tabIndex == index) Blue else HintColor,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.W600,
                            fontFamily = FontFamily(Font(R.font.quicksand_semi_bold))
                        )
                    )
                },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index }
                )
            }
        }
        when (tabIndex) {
            0 -> ScienceSubjects(
                navController = navController,
                subjectsViewModel = subjectsViewModel,
                classId = classId
            )
            1 -> CommerceSubjects(
                navController = navController,
                subjectsViewModel = subjectsViewModel,
                classId = classId
            )
            2 -> ArtsSubjects(
                navController = navController,
                subjectsViewModel = subjectsViewModel,
                classId = classId
            )
        }
    }
}