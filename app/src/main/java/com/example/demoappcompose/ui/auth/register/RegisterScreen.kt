package com.example.demoappcompose.ui.auth.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.demoappcompose.R
import com.example.demoappcompose.ui.auth.components.TopImage
import com.example.demoappcompose.ui.components.CustomDropDown
import com.example.demoappcompose.ui.components.CustomTextField
import com.example.demoappcompose.ui.components.MainButton
import com.example.demoappcompose.ui.VerticalSpacer
import com.example.demoappcompose.ui.screenPadding
import com.example.demoappcompose.ui.navigation.Screens
import com.example.demoappcompose.ui.theme.Blue
import com.example.demoappcompose.ui.theme.HintColor
import com.example.demoappcompose.ui.theme.TitleColor

@Composable
fun RegisterScreen(navController: NavController) {

    Box(
        modifier = Modifier.background(color = Color.White)
    ) {
        Column {

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

            var mExpanded by remember { mutableStateOf(false) }
            val items = listOf("Teacher", "Student", "Principal")
            var mSelectedText by remember { mutableStateOf("") }
            var postError by remember { mutableStateOf(false) }

            val scrollState = rememberScrollState()

            TopImage()

            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(screenPadding())
                        .verticalScroll(scrollState)
                ) {

                    Text(
                        text = "Register",
                        style = TextStyle(
                            color = TitleColor,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.W700,
                            fontFamily = FontFamily(Font(R.font.quicksand_medium)),
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    VerticalSpacer(size = 25)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_register_top),
                            contentDescription = "",
                            modifier = Modifier
                                .width(182.dp)
                                .height(119.dp)
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
                                localFocusManager.moveFocus(FocusDirection.Down)
                            }
                        },
                        onValueChange = {
                            if (it.length <= 10) mobileNum = it
                        })

                    VerticalSpacer(size = 15)

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

                    CustomTextField(modifier = Modifier
                        .fillMaxWidth(),
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

                    CustomTextField(modifier = Modifier
                        .fillMaxWidth()
                        ,
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

                    CustomTextField(modifier = Modifier
                        .fillMaxWidth(),
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
                    
                    CustomDropDown(
                        mExpanded = mExpanded,
                        items = items,
                        mSelectedText = mSelectedText,
                        onClick = {
                            mExpanded = mExpanded.not()
                        },
                        onDismissRequest = {
                            mExpanded = false
                        }
                    ) { label ->
                        mSelectedText = label
                        mExpanded = false
                    }

                    if (postError) {
                        Text(
                            text = "Please select your post", color = Color.Red,
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                }

                Column(
                    modifier = Modifier.padding(screenPadding())
                ) {
                    MainButton(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = "Register"
                    ) {
                        nameError = name.isEmpty()
                        emailError = email.isEmpty()
                        cityError = city.isEmpty()
                        postError = mSelectedText.isEmpty()

                        if (nameError.not() and emailError.not() and cityError.not() and postError.not()) {
                            navController.navigate(Screens.Dashboard.route)
                        }
                    }

                    VerticalSpacer(size = 12)

                    Text(
                        text = "Already have an account?",
                        style = TextStyle(
                            color = HintColor,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W500,
                            fontFamily = FontFamily(Font(R.font.quicksand_medium)),
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    VerticalSpacer(size = 5)

                    Text(
                        text = "Login",
                        style = TextStyle(
                            color = Blue,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W700,
                            fontFamily = FontFamily(Font(R.font.quicksand_bold)),
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(Screens.LoginScreen.route)
                            }
                    )
                }
            }
        }
    }

}