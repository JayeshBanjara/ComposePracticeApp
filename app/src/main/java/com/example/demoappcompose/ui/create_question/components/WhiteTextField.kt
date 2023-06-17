package com.example.demoappcompose.ui.create_question.components

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.demoappcompose.R
import com.example.demoappcompose.ui.theme.HintColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WhiteTextField(
    modifier: Modifier,
    text: String,
    placeholderText: String = "",
    keyboardType: KeyboardType,
    imeAction: ImeAction,
    onNext: () -> Unit,
    onValueChange: (value: String) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    BasicTextField(
        value = text,
        modifier = modifier
            .clip(shape = RoundedCornerShape(5.dp))
            .background(
                color = Color.White
            )
            .height(40.dp),
        onValueChange = {
            onValueChange(it)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType, imeAction = imeAction,
        ),
        keyboardActions = KeyboardActions(onNext = { onNext() }),
        singleLine = true,
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 12.sp,
            fontWeight = FontWeight.W500,
            fontFamily = FontFamily(Font(R.font.quicksand_medium)),
            textAlign = TextAlign.Center
        ),
        interactionSource = interactionSource,
    ) { innerTextField ->
        TextFieldDefaults.TextFieldDecorationBox(
            value = text,
            placeholder = {
                Text(
                    text = placeholderText,
                    style = TextStyle(
                        color = HintColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W500,
                        fontFamily = FontFamily(Font(R.font.quicksand_medium)),
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                focusedIndicatorColor = Color.White,
                unfocusedContainerColor = Color.White,
                unfocusedIndicatorColor = Color.White,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            innerTextField = innerTextField,
            interactionSource = interactionSource,
            contentPadding = PaddingValues(0.dp),
            enabled = true,
            visualTransformation = VisualTransformation.None
        )
    }

}