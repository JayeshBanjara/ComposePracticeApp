package com.example.demoappcompose.ui.auth.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.demoappcompose.R
import com.example.demoappcompose.ui.components.CustomTextField
import com.example.demoappcompose.ui.components.CustomTopAppBar
import com.example.demoappcompose.ui.components.MainButton
import com.example.demoappcompose.ui.components.VerticalSpacer
import com.example.demoappcompose.ui.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {
    Scaffold(modifier = Modifier, topBar = {
        CustomTopAppBar(title = "Login")
    }) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            var mobileNum by remember { mutableStateOf("") }
            var emptyNumError by remember { mutableStateOf(false) }
            var otp by remember { mutableStateOf("") }
            var otpError by remember { mutableStateOf(false) }
            var showOTPView by remember { mutableStateOf(false) }
            var buttonLabel by remember { mutableStateOf("Send OTP") }

            val localFocusManager = LocalFocusManager.current

            VerticalSpacer(size = 30)
            Image(
                painter = painterResource(id = R.drawable.ic_app_logo),
                contentDescription = "",
                modifier = Modifier.size(120.dp)
            )
            VerticalSpacer(size = 50)
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
                    if (mobileNum.length == 10) {
                        emptyNumError = false
                        showOTPView = true
                        buttonLabel = "SUBMIT"
                        localFocusManager.moveFocus(FocusDirection.Down)
                    }
                },
                onValueChange = {
                    if (it.length <= 10) mobileNum = it
                })

            VerticalSpacer(size = 20)

            AnimatedVisibility(visible = showOTPView) {

                CustomTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    text = otp,
                    labelText = "Enter OTP",
                    placeholderText = "x x x x x",
                    keyboardType = KeyboardType.NumberPassword,
                    imeAction = ImeAction.Done,
                    isError = otpError,
                    errorText = "Please enter valid OTP",
                    onNext = {
                        if (mobileNum.length == 5) {
                            otpError = false
                            localFocusManager.clearFocus()
                        }
                    },
                    onValueChange = {
                        if (it.length <= 5) otp = it
                    }
                )

            }

            VerticalSpacer(size = 60)

            MainButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(horizontal = 100.dp),
                text = buttonLabel
            ) {
                emptyNumError = mobileNum.length < 10
                if (showOTPView) {
                    otpError = otp.length < 5
                }

                if (mobileNum.length == 10) {
                    showOTPView = true
                    buttonLabel = "SUBMIT"
                }

                if ((mobileNum.length == 10) and (otp.length == 5)) {
                    navController.navigate(Screens.RegisterScreen.route)
                }
            }
        }
    }
}