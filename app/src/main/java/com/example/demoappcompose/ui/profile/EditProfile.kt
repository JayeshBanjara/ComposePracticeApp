package com.example.demoappcompose.ui.profile

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material3.Icon
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
import androidx.compose.ui.layout.ContentScale
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
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.demoappcompose.R
import com.example.demoappcompose.ui.HorizontalSpacer
import com.example.demoappcompose.ui.VerticalSpacer
import com.example.demoappcompose.ui.components.CustomTextFieldDialog
import com.example.demoappcompose.ui.components.CustomTopAppBar
import com.example.demoappcompose.ui.components.MainButton
import com.example.demoappcompose.ui.dashboard.home.components.HeaderText
import com.example.demoappcompose.ui.screenPadding
import com.example.demoappcompose.ui.theme.Blue
import com.example.demoappcompose.ui.theme.TitleColor

@Composable
fun EditProfile(navController: NavController) {
    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = "Edit Profile",
                showBack = true,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    ) { innerPadding ->

        val localFocusManager = LocalFocusManager.current
        var mobileNum by remember { mutableStateOf("9874561200") }
        var emptyNumError by remember { mutableStateOf(false) }
        var name by remember { mutableStateOf("Suraj Lodhi") }
        var nameError by remember { mutableStateOf(false) }
        var email by remember { mutableStateOf("suraj@gmail.com") }
        var emailError by remember { mutableStateOf(false) }
        var selectedUri by remember { mutableStateOf("") }
        val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { uri ->
                if (uri != null) {
                    selectedUri = uri.toString()
                }
            }
        )
        val logoImageSize = 150.dp

        Column(
            modifier = Modifier
                .padding(
                    start = screenPadding(),
                    top = innerPadding.calculateTopPadding(),
                    end = screenPadding(),
                    bottom = screenPadding()
                )
                .fillMaxSize()
        ) {

            val scrollState = rememberScrollState()

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(scrollState)
                    .background(color = Color.White)
            ) {
                VerticalSpacer(size = 5)
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

                VerticalSpacer(size = 12)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .border(
                                width = 1.dp,
                                color = Blue,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(8.dp)
                            )
                    ) {
                        if (selectedUri.isEmpty()) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_app_logo),
                                contentDescription = null,
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier
                                    .width(logoImageSize)
                                    .height(logoImageSize)
                            )
                        } else {
                            AsyncImage(
                                model = selectedUri,
                                contentDescription = null,
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier
                                    .width(logoImageSize)
                                    .height(logoImageSize)
                            )
                        }

                    }

                    HorizontalSpacer(size = 25)

                    Column(
                        modifier = Modifier
                            .wrapContentSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .background(color = Blue, shape = CircleShape)
                                .clickable {
                                    singlePhotoPickerLauncher.launch(
                                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                    )
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.UploadFile,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }

                        VerticalSpacer(size = 5)

                        Text(
                            text = "Update Logo",
                            style = TextStyle(
                                color = TitleColor,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.W400,
                                fontFamily = FontFamily(Font(R.font.quicksand_medium))
                            )
                        )
                    }
                }
            }

            MainButton(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(R.string.save)
            ) {
                emptyNumError = mobileNum.length < 10
                nameError = name.isEmpty()
                emailError = email.isEmpty()

                if ((!emptyNumError) and (!nameError) and (!emailError)) {
                    navController.popBackStack()
                }
            }
        }
    }
}