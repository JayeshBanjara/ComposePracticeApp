package com.example.demoappcompose.ui.auth.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.demoappcompose.R
import com.example.demoappcompose.data.responses.register_response.MediumData
import com.example.demoappcompose.ui.HorizontalSpacer
import com.example.demoappcompose.ui.theme.Blue
import com.example.demoappcompose.ui.theme.TitleColor

@Composable
fun MediumsLayout(
    mediums: List<MediumData>,
    isHindiChecked: Boolean,
    onHindiCheckChanged: () -> Unit,
    isGujaratiChecked: Boolean,
    onGujaratiCheckChanged: () -> Unit,
    isEnglishChecked: Boolean,
    onEnglishCheckChanged: () -> Unit
) {
    Column {
        Row {
            HorizontalSpacer(size = 5)
            Text(
                text = "Select medium",
                style = TextStyle(
                    color = TitleColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500,
                    fontFamily = FontFamily(Font(R.font.quicksand_medium))
                )
            )
        }
        Row {
            if(mediums.isNotEmpty())
            CheckBoxLayout(
                text = mediums[0].mediumName,
                isChecked = isHindiChecked,
                onCheckedChange = { onHindiCheckChanged() }
            )

            if(mediums.size > 1)
            CheckBoxLayout(
                text = mediums[1].mediumName,
                isChecked = isGujaratiChecked,
                onCheckedChange = { onGujaratiCheckChanged() }
            )

            if(mediums.size > 2)
            CheckBoxLayout(
                text = mediums[2].mediumName,
                isChecked = isEnglishChecked,
                onCheckedChange = { onEnglishCheckChanged() }
            )
        }
    }
}

@Composable
fun CheckBoxLayout(text: String, isChecked: Boolean, onCheckedChange: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = { onCheckedChange() },
            colors = CheckboxDefaults.colors(
                checkedColor = Blue
            )
        )
        Text(
            text = text,
            style = TextStyle(
                color = TitleColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.W500,
                fontFamily = FontFamily(Font(R.font.quicksand_medium))
            )
        )
    }
}