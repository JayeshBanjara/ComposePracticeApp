package com.example.demoappcompose.ui.auth.register

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.demoappcompose.R
import com.example.demoappcompose.data.responses.register_response.MediumData
import com.example.demoappcompose.data.responses.register_response.RoleData
import com.example.demoappcompose.ui.HorizontalSpacer
import com.example.demoappcompose.ui.VerticalSpacer
import com.example.demoappcompose.ui.auth.components.MediumsLayout
import com.example.demoappcompose.ui.auth.components.TopImage
import com.example.demoappcompose.ui.components.CustomTextField
import com.example.demoappcompose.ui.components.Loader
import com.example.demoappcompose.ui.components.MainButton
import com.example.demoappcompose.ui.navigation.Screens
import com.example.demoappcompose.ui.screenPadding
import com.example.demoappcompose.ui.theme.Blue
import com.example.demoappcompose.ui.theme.GreyDark
import com.example.demoappcompose.ui.theme.GreyLight
import com.example.demoappcompose.ui.theme.HintColor
import com.example.demoappcompose.ui.theme.TitleColor
import com.example.demoappcompose.utility.UiState
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    navController: NavController, registerViewModel: RegisterViewModel
) {

    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Image(
            painter = painterResource(id = R.drawable.screen_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

        Column {
            val getRolesState by remember { registerViewModel.getRolesState }.collectAsStateWithLifecycle()
            when (getRolesState) {
                is UiState.Loading -> {
                    Loader()
                }

                is UiState.Empty -> {}

                is UiState.UnAuthorised -> {}

                is UiState.Error -> {
                    val errorMessage = (getRolesState as UiState.Error).errorMessage
                    LaunchedEffect(Unit) {
                        Toast.makeText(
                            context, errorMessage, Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                is UiState.Success -> {

                    Box(modifier = Modifier.fillMaxSize()) {
                        val data = (getRolesState as UiState.Success).data.roleMediumData
                        MainContent(
                            navController = navController,
                            registerViewModel = registerViewModel,
                            roleData = data.roleData,
                            mediumData = data.mediumData
                        )

                        val registerState by remember { registerViewModel.registerState }.collectAsStateWithLifecycle()
                        when (registerState) {
                            UiState.Empty -> {}

                            is UiState.UnAuthorised -> {}

                            is UiState.Error -> {
                                val errorMessage = (registerState as UiState.Error).errorMessage
                                LaunchedEffect(Unit) {
                                    Toast.makeText(
                                        context, errorMessage, Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }

                            is UiState.Loading -> {
                                Loader()
                            }

                            is UiState.Success -> {
                                LaunchedEffect(Unit) {
                                    val message = (registerState as UiState.Success).data.message
                                    Toast.makeText(
                                        context, message, Toast.LENGTH_SHORT
                                    ).show()
                                    navController.navigate(Screens.LoginScreen.route) {
                                        popUpTo(Screens.SplashScreen.route) {
                                            inclusive = true
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(
    navController: NavController,
    registerViewModel: RegisterViewModel,
    roleData: List<RoleData>,
    mediumData: List<MediumData>
) {

    val localFocusManager = LocalFocusManager.current
    var mobileNum by remember { mutableStateOf("") }
    var emptyNumError by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf(false) }
    var password by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf(false) }
    var cnfPwd by remember { mutableStateOf("") }
    var cnfPwdError by remember { mutableStateOf(false) }
    var instituteName by remember { mutableStateOf("") }
    var instituteError by remember { mutableStateOf(false) }
    val uri: Uri? = null
    var instituteLogoName by remember { mutableStateOf(uri) }
    var instituteLogoError by remember { mutableStateOf(false) }
    var city by remember { mutableStateOf("") }
    var cityError by remember { mutableStateOf(false) }
    var mExpanded by remember { mutableStateOf(false) }
    var selectedRole by remember { mutableStateOf(roleData[0]) }
    var postError by remember { mutableStateOf(false) }
    var isHindiChecked by remember { mutableStateOf(false) }
    var isGujaratiChecked by remember { mutableStateOf(false) }
    var isEnglishChecked by remember { mutableStateOf(false) }
    var mediumError by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { selectedUri ->
            if (selectedUri != null) {
                instituteLogoName = selectedUri
            }
        })

    Column(modifier = Modifier.fillMaxSize()) {
        TopImage()

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(screenPadding())
                    .verticalScroll(scrollState)
            ) {

                Text(
                    text = "Register", style = TextStyle(
                        color = TitleColor,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.W700,
                        fontFamily = FontFamily(Font(R.font.quicksand_medium)),
                        textAlign = TextAlign.Center
                    ), modifier = Modifier.fillMaxWidth()
                )

                VerticalSpacer(size = 25)

                Row(
                    modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
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

                CustomTextField(modifier = Modifier.fillMaxWidth(),
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

                VerticalSpacer(size = 15)

                CustomTextField(modifier = Modifier.fillMaxWidth(),
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

                VerticalSpacer(size = 15)

                CustomTextField(modifier = Modifier.fillMaxWidth(),
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

                VerticalSpacer(size = 15)

                CustomTextField(modifier = Modifier.fillMaxWidth(),
                    text = password,
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next,
                    isError = passwordError,
                    errorText = "Please enter password",
                    placeholderText = "Enter password",
                    onNext = {
                        localFocusManager.moveFocus(FocusDirection.Down)
                    },
                    onValueChange = {
                        password = it
                        passwordError = false
                    })

                VerticalSpacer(size = 15)

                CustomTextField(modifier = Modifier.fillMaxWidth(),
                    text = cnfPwd,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    isError = cnfPwdError,
                    errorText = "Password and confirm password does not match",
                    placeholderText = "Enter confirm password",
                    onNext = {
                        localFocusManager.moveFocus(FocusDirection.Down)
                    },
                    onValueChange = {
                        cnfPwd = it
                        cnfPwdError = false
                    })

                VerticalSpacer(size = 15)

                CustomTextField(modifier = Modifier.fillMaxWidth(),
                    text = instituteName,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    isError = instituteError,
                    placeholderText = stringResource(id = R.string.enter_your_institute),
                    errorText = "Please enter institute name",
                    onNext = {
                        localFocusManager.moveFocus(FocusDirection.Down)
                    },
                    onValueChange = {
                        if (it.length <= 50) instituteName = it
                        instituteError = false
                    })

                VerticalSpacer(size = 15)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(modifier = Modifier
                        .weight(1f)
                        .wrapContentHeight(),
                        readOnly = true,
                        maxLines = 1,
                        singleLine = true,
                        textStyle = TextStyle(
                            color = TitleColor,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W500,
                            fontFamily = FontFamily(Font(R.font.quicksand_medium))
                        ),
                        value = if(instituteLogoName != null) instituteLogoName.toString() else "Choose Image",
                        shape = RoundedCornerShape(50.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = GreyLight,
                            unfocusedBorderColor = GreyLight,
                            focusedLabelColor = GreyLight,
                            cursorColor = GreyDark,
                            containerColor = GreyLight
                        ),
                        placeholder = {
                            Text(
                                text = "Upload institute logo", style = TextStyle(
                                    color = HintColor,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.W500,
                                    fontFamily = FontFamily(Font(R.font.quicksand_medium))
                                )
                            )
                        },
                        onValueChange = {})

                    HorizontalSpacer(size = 5)

                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .background(color = Blue, shape = CircleShape)
                            .clickable {
                                singlePhotoPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            }, contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_upload),
                            contentDescription = null,
                            tint = Color.White
                        )
                    }

                }
                if (instituteLogoError) {
                    Text(
                        text = "Please upload institute logo", color = Color.Red,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                    )
                }

                VerticalSpacer(size = 15)

                CustomTextField(modifier = Modifier.fillMaxWidth(),
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
                        cityError = false
                    })

                VerticalSpacer(size = 15)

                RoleDropDown(mExpanded = mExpanded, items = roleData, role = selectedRole, onClick = {
                    mExpanded = mExpanded.not()
                }, onDismissRequest = {
                    mExpanded = false
                }) { role ->
                    selectedRole = role
                    mExpanded = false
                }

                if (postError) {
                    Text(
                        text = "Please select your role", color = Color.Red,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }

                VerticalSpacer(size = 15)

                MediumsLayout(mediums = mediumData,
                    isHindiChecked = isHindiChecked,
                    onHindiCheckChanged = {
                        isHindiChecked = isHindiChecked.not()
                    },
                    isGujaratiChecked = isGujaratiChecked,
                    onGujaratiCheckChanged = {
                        isGujaratiChecked = isGujaratiChecked.not()
                    },
                    isEnglishChecked = isEnglishChecked,
                    onEnglishCheckChanged = {
                        isEnglishChecked = isEnglishChecked.not()
                    })
                if (mediumError) {
                    Text(
                        text = "Please select medium", color = Color.Red,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }

                VerticalSpacer(size = 15)

                MainButton(
                    modifier = Modifier.fillMaxWidth(), text = "Register"
                ) {


                    emptyNumError = mobileNum.length < 10
                    nameError = name.isEmpty()
                    emailError = email.isEmpty()
                    passwordError = password.isEmpty()
                    cnfPwdError = cnfPwd.isEmpty()
                    instituteError = instituteName.isEmpty()
                    instituteLogoError = instituteLogoName == null
                    cityError = city.isEmpty()
                    postError = false
                    mediumError =
                        isEnglishChecked.not() and isGujaratiChecked.not() and isHindiChecked.not()

                    if (emptyNumError.not() and nameError.not() and emailError.not() and passwordError.not() and cnfPwdError.not() and instituteError.not() and instituteLogoError.not() and cityError.not() and postError.not() and mediumError.not()) {

                        val mediumIds = mutableListOf<String>()

                        if (isHindiChecked) {
                            mediumIds.add(mediumData[0].id.toString())
                        }
                        if (isGujaratiChecked) {
                            mediumIds.add(mediumData[1].id.toString())
                        }
                        if (isEnglishChecked) {
                            mediumIds.add(mediumData[2].id.toString())
                        }

                        coroutineScope.launch {
                            registerViewModel.register(
                                mobileNo = mobileNum,
                                fullName = name,
                                email = email,
                                password = password,
                                instituteName = instituteName,
                                instituteLogo = instituteLogoName!!,
                                city = city,
                                roleId = selectedRole.id.toString(),
                                mediumIds = mediumIds
                            )
                        }
                    }
                }

                VerticalSpacer(size = 12)

                Text(
                    text = "Already have an account?", style = TextStyle(
                        color = HintColor,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.W500,
                        fontFamily = FontFamily(Font(R.font.quicksand_medium)),
                        textAlign = TextAlign.Center
                    ), modifier = Modifier.fillMaxWidth()
                )

                VerticalSpacer(size = 5)

                Text(text = "Login", style = TextStyle(
                    color = Blue,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W700,
                    fontFamily = FontFamily(Font(R.font.quicksand_bold)),
                    textAlign = TextAlign.Center
                ), modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate(Screens.LoginScreen.route)
                    })
            }
        }
    }
}