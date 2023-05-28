package com.example.demoappcompose.ui.dashboard.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import com.example.demoappcompose.ui.VerticalSpacer
import com.example.demoappcompose.ui.components.CustomTextFieldDialog
import com.example.demoappcompose.ui.components.MainButton
import com.example.demoappcompose.ui.theme.Blue
import com.example.demoappcompose.ui.theme.TitleColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileDialog(onDismissRequest: () -> Unit) {

    val localFocusManager = LocalFocusManager.current
    var mobileNum by remember { mutableStateOf("9874561200") }
    var emptyNumError by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("Suraj Lodhi") }
    var nameError by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("suraj@gmail.com") }
    var emailError by remember { mutableStateOf(false) }

    AlertDialog(onDismissRequest = { onDismissRequest() }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .background(color = Blue)
                    .padding(10.dp),
            ) {
                Text(
                    text = "Edit Profile",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.W700,
                        fontFamily = FontFamily(Font(R.font.quicksand_bold))
                    ),
                    modifier = Modifier.align(Alignment.Center)
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = "Close Pop-up",
                    tint = Color.White,
                    modifier = Modifier
                        .size(20.dp)
                        .align(Alignment.CenterEnd)
                        .clickable { onDismissRequest() }
                )
            }

            val scrollState = rememberScrollState()

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .verticalScroll(scrollState)
                    .background(color = Color.White)
                    .padding(10.dp)
            ) {
                HeaderText(text = "Enter Mobile Number")
                VerticalSpacer(size = 5)
                CustomTextFieldDialog(modifier = Modifier
                    .fillMaxWidth(),
                    text = mobileNum,
                    placeholderText = stringResource(R.string.enter_your_mobile_number),
                    keyboardType = KeyboardType.NumberPassword,
                    imeAction = ImeAction.Next,
                    isError = emptyNumError,
                    errorText = stringResource(R.string.please_enter_valid_mobile_number),
                    onNext = {
                        localFocusManager.moveFocus(FocusDirection.Down)
                    },
                    onValueChange = {
                        if (it.length <= 10) mobileNum = it
                    })
                VerticalSpacer(size = 8)
                HeaderText(text = "Enter Your Name")
                VerticalSpacer(size = 5)
                CustomTextFieldDialog(modifier = Modifier
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
                VerticalSpacer(size = 8)
                HeaderText(text = "Enter Your Mail-ID")
                VerticalSpacer(size = 5)
                CustomTextFieldDialog(modifier = Modifier
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

                VerticalSpacer(size = 10)

                MainButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = stringResource(R.string.save)
                ) {
                    emptyNumError = mobileNum.length < 10
                    nameError = name.isEmpty()
                    emailError = email.isEmpty()

                    if ((!emptyNumError) and (!nameError) and (!emailError)) {
                        onDismissRequest()
                    }
                }

                VerticalSpacer(size = 5)
            }
        }
    }
}

@Composable
fun HeaderText(text: String) {
    Text(
        text = text,
        style = TextStyle(
            color = TitleColor,
            fontSize = 15.sp,
            fontWeight = FontWeight.W600,
            fontFamily = FontFamily(Font(R.font.quicksand_semi_bold))
        )
    )
}

