package com.example.demoappcompose.ui.auth.login

import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.demoappcompose.R
import com.example.demoappcompose.ui.VerticalSpacer
import com.example.demoappcompose.ui.auth.components.TopImage
import com.example.demoappcompose.ui.auth.login.components.PasswordField
import com.example.demoappcompose.ui.components.CustomTextField
import com.example.demoappcompose.ui.components.Loader
import com.example.demoappcompose.ui.components.MainButton
import com.example.demoappcompose.ui.navigation.Screens
import com.example.demoappcompose.ui.screenPadding
import com.example.demoappcompose.ui.theme.Blue
import com.example.demoappcompose.ui.theme.HintColor
import com.example.demoappcompose.ui.theme.TitleColor
import com.example.demoappcompose.utility.UiState
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel
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

        val state by remember { loginViewModel.uiState }.collectAsStateWithLifecycle()

        MainContent(navController = navController, loginViewModel = loginViewModel)

        when (state) {
            is UiState.Loading -> { Loader() }

            UiState.Empty -> {}

            is UiState.Error -> {
                val errorMessage = (state as UiState.Error).data
                LaunchedEffect(Unit) {
                    Toast.makeText(
                        context,
                        errorMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            is UiState.Success -> {
                LaunchedEffect(Unit) {
                    val loginData = (state as UiState.Success).data.loginData
                    navController.navigate(Screens.Dashboard.withArgs(loginData.userData[0].userId.toString())) {
                        popUpTo(Screens.SplashScreen.route) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MainContent(
    navController: NavController,
    loginViewModel: LoginViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

       var mobileNum by remember { mutableStateOf("") }
        //var mobileNum by remember { mutableStateOf("7226941148") }
        var emptyNumError by remember { mutableStateOf(false) }
        var password by remember { mutableStateOf("") }
        //var password by remember { mutableStateOf("Admin@123") }
        var passwordError by remember { mutableStateOf(false) }
        var passwordVisibility: Boolean by remember { mutableStateOf(false) }
        val scrollState = rememberScrollState()
        val localFocusManager = LocalFocusManager.current
        val coroutineScope = rememberCoroutineScope()

        TopImage()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(screenPadding())
                .verticalScroll(scrollState)
        ) {

            Text(
                text = stringResource(id = R.string.login),
                style = TextStyle(
                    color = TitleColor,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.W700,
                    fontFamily = FontFamily(Font(R.font.quicksand_medium)),
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.fillMaxWidth()
            )

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

            VerticalSpacer(size = 40)

            CustomTextField(modifier = Modifier
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

            VerticalSpacer(size = 10)

            PasswordField(
                text = password,
                isError = passwordError,
                passwordVisibility = passwordVisibility,
                onTrailingIconClick = {
                    passwordVisibility = !passwordVisibility
                },
                onNext = { localFocusManager.moveFocus(FocusDirection.Exit) },
                onValueChange = {
                    password = it
                }
            )

            VerticalSpacer(size = 50)

            MainButton(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(id = R.string.login)
            ) {
                emptyNumError = mobileNum.length < 10
                passwordError = password.isEmpty()

                if ((!emptyNumError) and (password.isNotEmpty())) {

                    coroutineScope.launch {
                        loginViewModel.login(
                            mobileNum = mobileNum,
                            password = password
                        )
                    }
                }
            }

            /*VerticalSpacer(size = 8)

            Text(
                text = "or",
                style = TextStyle(
                    color = HintColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W500,
                    fontFamily = FontFamily(Font(R.font.quicksand_medium)),
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.fillMaxWidth()
            )

            VerticalSpacer(size = 8)

            MainButton(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "Login with OTP"
            ) {
                //Navigate to login with OTP screen
            }
*/
            VerticalSpacer(size = 12)

            Text(
                text = "Don’t have an account?",
                style = TextStyle(
                    color = HintColor,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.W500,
                    fontFamily = FontFamily(Font(R.font.quicksand_medium)),
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.fillMaxWidth()
            )

            VerticalSpacer(size = 5)

            Text(
                text = "Sign Up",
                style = TextStyle(
                    color = Blue,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W700,
                    fontFamily = FontFamily(Font(R.font.quicksand_bold)),
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        loginViewModel._uiState.value = UiState.Empty
                        navController.navigate(Screens.RegisterScreen.route)
                    }
            )
        }
    }
}