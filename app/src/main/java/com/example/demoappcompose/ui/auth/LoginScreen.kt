package com.example.demoappcompose.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.demoappcompose.R
import com.example.demoappcompose.ui.components.CustomTopAppBar
import com.example.demoappcompose.ui.components.VerticalSpacer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(name: String?) {
    Scaffold(modifier = Modifier, topBar = {
        CustomTopAppBar()
    }) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            var mobileNum by remember { mutableStateOf("") }
            var emptyNumError by remember { mutableStateOf(false) }

            VerticalSpacer(size = 30)
            Image(
                painter = painterResource(id = R.drawable.ic_app_logo),
                contentDescription = "",
                modifier = Modifier.size(120.dp)
            )
            VerticalSpacer(size = 50)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                OutlinedTextField(
                    value = mobileNum,
                    onValueChange = {
                        if (it.length <= 10) mobileNum = it
                    },
                    label = {
                        Text(text = "Enter Mobile Number")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                    modifier = Modifier.fillMaxWidth()
                )

                if (emptyNumError) {
                    Text(
                        text = "Please enter valid mobile number", color = Color.Red
                    )
                }
            }

            VerticalSpacer(size = 70)

            OutlinedButton(onClick = {
                emptyNumError = mobileNum.isEmpty() or (mobileNum.length < 10)
            }) {
                Text(text = "SUBMIT")
            }
        }
    }
}