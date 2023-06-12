package com.example.demoappcompose.ui.create_question.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import com.example.demoappcompose.R
import com.example.demoappcompose.ui.theme.HintColor
import com.example.demoappcompose.ui.theme.TitleColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WhiteTextField(
    modifier: Modifier,
    text: String,
    placeholderText: String = "",
    keyboardType: KeyboardType,
    imeAction: ImeAction,
    minLines: Int = 1,
    maxLines: Int = 1,
    readOnly: Boolean = false,
    enabled: Boolean = true,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.None,
    onNext: () -> Unit,
    onValueChange: (value: String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        value = text,
        enabled = enabled,
        textStyle = TextStyle(
            color = TitleColor,
            fontSize = 10.sp,
            fontWeight = FontWeight.W300,
            fontFamily = FontFamily(Font(R.font.quicksand_medium))
        ),
        shape = RectangleShape,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.White,
            focusedLabelColor = Color.White,
            cursorColor = Color.White,
            containerColor = Color.White
        ),
        onValueChange = { onValueChange(it) },
        placeholder = {
            Text(
                text = placeholderText,
                style = TextStyle(
                    color = HintColor,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.W300,
                    fontFamily = FontFamily(Font(R.font.quicksand_medium))
                )
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType, imeAction = imeAction,
            capitalization = capitalization
        ),
        keyboardActions = KeyboardActions(onNext = { onNext() }),
        minLines = minLines,
        maxLines = maxLines,
        readOnly = readOnly
    )
}