package com.example.demoappcompose.ui.profile

import androidx.activity.compose.BackHandler
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.demoappcompose.R
import com.example.demoappcompose.ui.HorizontalSpacer
import com.example.demoappcompose.ui.VerticalSpacer
import com.example.demoappcompose.ui.components.CustomTextFieldDialog
import com.example.demoappcompose.ui.components.CustomTopAppBar
import com.example.demoappcompose.ui.components.Loader
import com.example.demoappcompose.ui.components.MainButton
import com.example.demoappcompose.ui.components.ScreenBackground
import com.example.demoappcompose.ui.navigation.Screens
import com.example.demoappcompose.ui.popUpToTop
import com.example.demoappcompose.ui.print_settings.HeaderText
import com.example.demoappcompose.ui.screenPadding
import com.example.demoappcompose.ui.theme.Blue
import com.example.demoappcompose.ui.theme.TitleColor
import com.example.demoappcompose.utility.UiState
import com.example.demoappcompose.utility.toast
import kotlinx.coroutines.launch

@Composable
fun EditProfile(
    navController: NavController,
    editProfileViewModel: EditProfileViewModel
) {

    val updatedProfilePic by remember { mutableStateOf(editProfileViewModel.profilePicState) }


    BackHandler {
        navController.previousBackStackEntry
            ?.savedStateHandle
            ?.set("updated_profile_pic", updatedProfilePic.value)
        navController.popBackStack()
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = "Edit Profile",
                showBack = true,
                onBackClick = {
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("updated_profile_pic", updatedProfilePic.value)
                    navController.popBackStack()
                }
            )
        }
    ) { innerPadding ->

        val localFocusManager = LocalFocusManager.current
        val context = LocalContext.current
        val coroutineScope = rememberCoroutineScope()

        val savedMobile = editProfileViewModel.numberState.collectAsStateWithLifecycle()
        var mobileNum by remember { mutableStateOf(savedMobile.value) }
        var emptyNumError by remember { mutableStateOf(false) }

        val savedName = editProfileViewModel.nameState.collectAsStateWithLifecycle()
        var name by remember { mutableStateOf(savedName.value) }
        var nameError by remember { mutableStateOf(false) }

        val savedEmail = editProfileViewModel.emailState.collectAsStateWithLifecycle()
        var email by remember { mutableStateOf(savedEmail.value) }
        var emailError by remember { mutableStateOf(false) }

        val savedProfilePicUrl = editProfileViewModel.profilePicState.collectAsStateWithLifecycle()
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

        Box(modifier = Modifier.fillMaxSize()) {

            ScreenBackground()

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
                            emptyNumError = false
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
                            nameError = false
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
                        onValueChange = {
                            if (it.length <= 50) email = it
                            emailError = false
                        })

                    VerticalSpacer(size = 12)

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .width(logoImageSize)
                                .height(logoImageSize)
                                .border(
                                    width = 1.dp,
                                    color = Blue,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .background(
                                    color = Color.White,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(5.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            if (selectedUri.isEmpty()) {

                                if (savedProfilePicUrl.value.isNotEmpty()) {
                                    AsyncImage(
                                        model = savedProfilePicUrl.value,
                                        contentDescription = null,
                                        contentScale = ContentScale.Fit
                                    )
                                } else {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_app_logo),
                                        contentDescription = null,
                                        contentScale = ContentScale.Fit
                                    )
                                }
                            } else {
                                AsyncImage(
                                    model = selectedUri,
                                    contentDescription = null,
                                    contentScale = ContentScale.Fit
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
                                    painter = painterResource(id = R.drawable.ic_upload),
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
                        coroutineScope.launch {
                            editProfileViewModel.updateProfile(
                                email = email,
                                fullName = name,
                                mobileNo = mobileNum,
                                insLogoUri = if (selectedUri.isNotEmpty()) selectedUri.toUri() else null
                            )
                        }
                    }
                }
            }

            val state by remember { editProfileViewModel.uiState }.collectAsStateWithLifecycle()
            when (state) {
                is UiState.Empty -> {}

                is UiState.UnAuthorised -> {
                    LaunchedEffect(Unit) {
                        val errorMessage = (state as UiState.UnAuthorised).errorMessage
                        context.toast(message = errorMessage)
                        navController.navigate(Screens.LoginScreen.route) {
                            popUpToTop(navController)
                        }
                    }
                }

                is UiState.Error -> {
                    val errorMessage = (state as UiState.Error).errorMessage
                    LaunchedEffect(Unit) {
                        context.toast(message = errorMessage)
                    }
                }

                is UiState.Loading -> {
                    Loader()
                }

                is UiState.Success -> {
                    val message = (state as UiState.Success).data.message
                    LaunchedEffect(Unit) {
                        context.toast(message = message)
                    }
                }
            }
        }
    }
}