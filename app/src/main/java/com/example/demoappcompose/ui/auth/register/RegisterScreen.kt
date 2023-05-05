package com.example.demoappcompose.ui.auth.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.demoappcompose.ui.components.CustomTextField
import com.example.demoappcompose.ui.components.CustomTopAppBar
import com.example.demoappcompose.ui.components.VerticalSpacer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavController) {
    Scaffold(modifier = Modifier, topBar = {
        CustomTopAppBar("Register")
    }) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            val localFocusManager = LocalFocusManager.current
            var mobileNum by remember { mutableStateOf("") }
            var emptyNumError by remember { mutableStateOf(false) }
            var name by remember { mutableStateOf("") }
            var nameError by remember { mutableStateOf(false) }
            var email by remember { mutableStateOf("") }
            var emailError by remember { mutableStateOf(false) }
            var instituteName by remember { mutableStateOf("") }
            var city by remember { mutableStateOf("") }
            var cityError by remember { mutableStateOf(false) }

            VerticalSpacer(size = 30)

            CustomTextField(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
                text = mobileNum,
                labelText = "Enter Mobile Number",
                keyboardType = KeyboardType.NumberPassword,
                imeAction = ImeAction.Next,
                isError = emptyNumError,
                errorText = "Please enter valid mobile number",
                onNext = {
                    localFocusManager.moveFocus(FocusDirection.Down)
                },
                onValueChange = {
                    if (it.length <= 10) mobileNum = it
                })

            VerticalSpacer(size = 10)

            CustomTextField(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
                text = name,
                labelText = "Enter Name",
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                isError = nameError,
                errorText = "Please enter name",
                onNext = {
                    localFocusManager.moveFocus(FocusDirection.Down)
                },
                onValueChange = {
                    if (it.length <= 50) name = it
                })

            VerticalSpacer(size = 10)

            CustomTextField(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
                text = email,
                labelText = "Enter Email",
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
                isError = emailError,
                errorText = "Please enter valid email",
                onNext = {
                    localFocusManager.moveFocus(FocusDirection.Down)
                },
                onValueChange = { if (it.length <= 50) email = it })

            VerticalSpacer(size = 10)

            CustomTextField(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
                text = instituteName,
                labelText = "Enter Institute Name",
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                isError = false,
                errorText = "",
                onNext = {
                    localFocusManager.moveFocus(FocusDirection.Down)
                },
                onValueChange = {
                    if (it.length <= 50) instituteName = it
                })

            VerticalSpacer(size = 10)

            CustomTextField(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
                text = city,
                labelText = "Enter City Name",
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done,
                isError = cityError,
                errorText = "Please enter city name",
                onNext = {
                    localFocusManager.clearFocus()
                },
                onValueChange = {
                    if (it.length <= 50) mobileNum = it
                })

            VerticalSpacer(size = 10)
        }
    }
}