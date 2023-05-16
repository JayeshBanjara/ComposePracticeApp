package com.example.demoappcompose.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.example.demoappcompose.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    modifier: Modifier,
    text: String,
    labelText: String = "",
    placeholderText: String = "",
    keyboardType: KeyboardType,
    imeAction: ImeAction,
    isError: Boolean,
    errorText: String,
    onNext: () -> Unit,
    onValueChange: (value: String) -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedTextField(
            modifier = modifier,
            value = text,
            onValueChange = { onValueChange(it) },
            label = {
                if (labelText.isNotEmpty()) Text(text = labelText)
            },
            placeholder = {
                if (placeholderText.isNotEmpty()) Text(text = placeholderText)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType, imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(onNext = { onNext() })
        )

        if (isError) {
            Text(
                text = errorText, color = Color.Red,
                modifier = modifier,
            )
        }
    }
}
