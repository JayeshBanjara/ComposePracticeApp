package com.example.demoappcompose.ui.auth.login

import PinView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.demoappcompose.R
import com.example.demoappcompose.ui.components.CustomTextField
import com.example.demoappcompose.ui.components.MainButton
import com.example.demoappcompose.ui.VerticalSpacer
import com.example.demoappcompose.ui.components.WhiteTopAppBar
import com.example.demoappcompose.ui.screenPadding
import com.example.demoappcompose.ui.navigation.Screens
import com.example.demoappcompose.ui.popUpToTop
import com.example.demoappcompose.ui.theme.Blue
import com.example.demoappcompose.ui.theme.TitleColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {
    Scaffold(modifier = Modifier, topBar = {
        WhiteTopAppBar(title = stringResource(R.string.login))
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

            var mobileNum by remember { mutableStateOf("") }
            var emptyNumError by remember { mutableStateOf(false) }
            var otp by remember { mutableStateOf("") }
            var otpError by remember { mutableStateOf(false) }
            var showOTPView by remember { mutableStateOf(false) }
            var buttonLabel by remember { mutableStateOf("Get OTP") }

            val localFocusManager = LocalFocusManager.current

            VerticalSpacer(size = 50)

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
                        showOTPView = true
                        buttonLabel = "SUBMIT"
                        localFocusManager.moveFocus(FocusDirection.Down)
                    }
                },
                onValueChange = {
                    if (it.length <= 10) mobileNum = it
                })

            VerticalSpacer(size = 40)

            AnimatedVisibility(visible = showOTPView) {

                Column() {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Enter the OTP send to ",
                            style = TextStyle(
                                color = TitleColor,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.W600,
                                fontFamily = FontFamily(Font(R.font.quicksand_medium))
                            )
                        )
                        Text(
                            text = "+91 $mobileNum",
                            style = TextStyle(
                                color = Blue,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.W700,
                                fontFamily = FontFamily(Font(R.font.quicksand_medium))
                            )
                        )
                    }

                    VerticalSpacer(size = 30)

                    PinView(
                        pinText = otp,
                        onPinTextChange = {
                            otp = it
                        }
                    )
                    VerticalSpacer(size = 5)
                    if (otpError) {
                        Text(
                            text = stringResource(id = R.string.please_enter_valid_otp),
                            color = Color.Red,
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                }
            }

            VerticalSpacer(size = 50)

            MainButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                text = buttonLabel
            ) {
                emptyNumError = mobileNum.length < 10
                if (showOTPView) {
                    otpError = otp.length < 4
                }

                if (mobileNum.length == 10) {
                    showOTPView = true
                    buttonLabel = "Verify & Proceed"
                }

                if ((mobileNum.length == 10) and (otp.length >= 4)) {
                    navController.navigate(Screens.RegisterScreen.route) {
                        popUpToTop(navController)
                    }
                }
            }
        }
    }
}