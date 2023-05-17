package com.example.demoappcompose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.demoappcompose.R

@Composable
fun CustomTextField(
    modifier: Modifier,
    text: String,
    placeholderText: String = "",
    keyboardType: KeyboardType,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.None,
    imeAction: ImeAction,
    readOnly: Boolean = false,
    isError: Boolean,
    errorText: String,
    onNext: () -> Unit,
    onValueChange: (value: String) -> Unit
) {

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .clip(RoundedCornerShape(corner = CornerSize(8.dp)))
                .background(colorResource(id = R.color.light_grey))
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(id = R.color.light_grey)),
                value = text,
                readOnly = readOnly,
                onValueChange = onValueChange,
                textStyle = TextStyle(
                    color = colorResource(id = R.color.text_hint_color)
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = keyboardType,
                    imeAction = imeAction
                ).copy(capitalization = capitalization),
                keyboardActions = KeyboardActions(onNext = { onNext() }),
                decorationBox = { innerTextField ->
                    Row(modifier = Modifier.fillMaxWidth()) {
                        if (text.isEmpty()) {
                            Text(
                                text = placeholderText,
                                color = colorResource(id = R.color.text_hint_color),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.W400,
                                fontFamily = FontFamily(Font(R.font.quicksand_medium))
                            )
                        }
                    }
                    innerTextField()
                }
            )
        }

        if (isError) {
            Text(
                text = errorText, color = Color.Red,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
