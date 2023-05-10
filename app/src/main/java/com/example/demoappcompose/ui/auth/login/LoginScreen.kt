package com.example.demoappcompose.ui.auth.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.demoappcompose.R
import com.example.demoappcompose.ui.components.VerticalSpacer
import com.example.demoappcompose.ui.components.WhiteTopAppBar
import com.example.demoappcompose.ui.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {
    Scaffold(modifier = Modifier, topBar = {
        WhiteTopAppBar(title = stringResource(R.string.login))
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
                painter = painterResource(id = R.drawable.ic_login_top),
                contentDescription = "",
                modifier = Modifier
                    .width(255.dp)
                    .height(200.dp)
            )
            VerticalSpacer(size = 30)
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
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.NumberPassword,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            if (mobileNum.length == 10) {
                                emptyNumError = false
                                showOTPView = true
                                buttonLabel = "SUBMIT"
                                localFocusManager.moveFocus(FocusDirection.Down)
                            }
                        }
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                if (emptyNumError) {
                    Text(
                        text = "Please enter valid mobile number", color = Color.Red
                    )
                }
            }

            VerticalSpacer(size = 20)

            AnimatedVisibility(visible = showOTPView) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    OutlinedTextField(
                        value = otp,
                        onValueChange = {
                            if (it.length <= 5) otp = it
                        },
                        label = {
                            Text(text = "Enter OTP")
                        },
                        placeholder = {
                            Text(text = "x x x x x")
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.NumberPassword,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                if (mobileNum.length == 5) {
                                    otpError = false
                                    localFocusManager.clearFocus()
                                }
                            }
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                    )

                    if (otpError) {
                        Text(
                            text = "Please enter valid OTP", color = Color.Red
                        )
                    }
                }
            }

            VerticalSpacer(size = 60)

            Button(
                onClick = {
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
                },
                shape = RoundedCornerShape(corner = CornerSize(5.dp)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(horizontal = 100.dp)
            ) {
                Text(text = buttonLabel)
            }
        }
    }
}