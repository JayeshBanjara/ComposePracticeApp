package com.example.demoappcompose.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
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
import com.example.demoappcompose.ui.HorizontalSpacer
import com.example.demoappcompose.ui.theme.Blue
import com.example.demoappcompose.ui.theme.GreyDark
import com.example.demoappcompose.ui.theme.GreyLight
import com.example.demoappcompose.ui.theme.HintColor
import com.example.demoappcompose.ui.theme.TitleColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    modifier: Modifier,
    text: String,
    labelText: String = "",
    placeholderText: String = "",
    keyboardType: KeyboardType,
    imeAction: ImeAction,
    minLines: Int = 1,
    maxLines: Int = 1,
    readOnly: Boolean = false,
    enabled: Boolean = true,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.None,
    isError: Boolean,
    errorText: String,
    trailingIcon: Painter? = null,
    onTrailingIconClick: () -> Unit = {},
    onNext: () -> Unit,
    onValueChange: (value: String) -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedTextField(
            modifier = modifier,
            value = text,
            enabled = enabled,
            textStyle = TextStyle(
                color = TitleColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.W500,
                fontFamily = FontFamily(Font(R.font.quicksand_medium))
            ),
            trailingIcon = {
                if (trailingIcon != null) {
                    Icon(
                        painter = trailingIcon,
                        contentDescription = null,
                        tint = Blue,
                        modifier = Modifier.clickable {
                            onTrailingIconClick()
                        }
                    )
                }
            },
            shape = RoundedCornerShape(50.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = GreyLight,
                unfocusedBorderColor = GreyLight,
                focusedLabelColor = GreyLight,
                cursorColor = GreyDark,
                containerColor = GreyLight
            ),
            onValueChange = { onValueChange(it) },
            placeholder = {
                Text(
                    text = placeholderText,
                    style = TextStyle(
                        color = HintColor,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W500,
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

        if (isError) {
            Row {
                HorizontalSpacer(size = 5)
                Text(
                    text = errorText, color = Color.Red,
                    modifier = modifier,
                )
            }
        }
    }
}