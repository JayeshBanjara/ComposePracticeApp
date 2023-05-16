package com.example.demoappcompose.ui.auth.register

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.demoappcompose.R
import com.example.demoappcompose.ui.components.CustomTextField
import com.example.demoappcompose.ui.components.CustomTopAppBar
import com.example.demoappcompose.ui.components.MainButton
import com.example.demoappcompose.ui.components.TextFieldHeader
import com.example.demoappcompose.ui.components.VerticalSpacer
import com.example.demoappcompose.ui.components.WhiteTopAppBar
import com.example.demoappcompose.ui.components.screenPadding
import com.example.demoappcompose.ui.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavController) {
    Scaffold(modifier = Modifier, topBar = {
        WhiteTopAppBar("Register")
    }) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(
                    top = contentPadding.calculateTopPadding(),
                    start = screenPadding(),
                    end = screenPadding(),
                    bottom = screenPadding()
                )
                .fillMaxSize()
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

            Column(
                modifier = Modifier.weight(1f).scrollable(rememberScrollState(), Orientation.Vertical)
            ) {

                VerticalSpacer(size = 30)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_login_top),
                        contentDescription = "",
                        modifier = Modifier
                            .width(255.dp)
                            .height(200.dp)
                    )
                }

                VerticalSpacer(size = 30)

                TextFieldHeader(headerText = stringResource(R.string.enter_mobile_number))

                VerticalSpacer(size = 5)

                CustomTextField(modifier = Modifier
                    .fillMaxWidth(),
                    text = mobileNum,
                    placeholderText = stringResource(R.string.enter_your_mobile_number),
                    keyboardType = KeyboardType.NumberPassword,
                    imeAction = ImeAction.Next,
                    isError = emptyNumError,
                    errorText = stringResource(R.string.please_enter_valid_mobile_number),
                    onNext = {
                        if (mobileNum.length == 10) {
                            emptyNumError = false
                            localFocusManager.moveFocus(FocusDirection.Down)
                        }
                    },
                    onValueChange = {
                        if (it.length <= 10) mobileNum = it
                    })

                VerticalSpacer(size = 15)

                TextFieldHeader(headerText = stringResource(R.string.enter_your_name_caps))

                VerticalSpacer(size = 5)

                CustomTextField(modifier = Modifier
                    .fillMaxWidth(),
                    text = name,
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next,
                    isError = nameError,
                    placeholderText = stringResource(id = R.string.enter_your_name),
                    errorText = "Please enter name",
                    onNext = {
                        localFocusManager.moveFocus(FocusDirection.Down)
                    },
                    onValueChange = {
                        if (it.length <= 50) name = it
                    })

                VerticalSpacer(size = 15)

                TextFieldHeader(headerText = stringResource(R.string.enter_your_mail_caps))

                VerticalSpacer(size = 5)

                CustomTextField(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                    text = email,
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                    isError = emailError,
                    errorText = "Please enter valid email",
                    placeholderText = stringResource(R.string.enter_your_mail),
                    onNext = {
                        localFocusManager.moveFocus(FocusDirection.Down)
                    },
                    onValueChange = { if (it.length <= 50) email = it })

                VerticalSpacer(size = 15)

                TextFieldHeader(headerText = stringResource(R.string.enter_your_institute_caps))

                VerticalSpacer(size = 5)

                CustomTextField(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                    text = instituteName,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    isError = false,
                    placeholderText = stringResource(id = R.string.enter_your_institute),
                    errorText = "",
                    onNext = {
                        localFocusManager.moveFocus(FocusDirection.Down)
                    },
                    onValueChange = {
                        if (it.length <= 50) instituteName = it
                    })

                VerticalSpacer(size = 15)

                TextFieldHeader(headerText = stringResource(R.string.city))

                VerticalSpacer(size = 5)

                CustomTextField(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                    text = city,
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Done,
                    isError = cityError,
                    placeholderText = stringResource(id = R.string.city_name),
                    errorText = "Please enter city name",
                    onNext = {
                        localFocusManager.clearFocus()
                    },
                    onValueChange = {
                        if (it.length <= 50) city = it
                    })

                VerticalSpacer(size = 15)

                TextFieldHeader(headerText = stringResource(R.string.select_your_post_caps))

                VerticalSpacer(size = 5)

                VerticalSpacer(size = 20)
            }

            MainButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                text = stringResource(R.string.submit)
            ) {

            }
        }
    }
}