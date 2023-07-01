package com.example.demoappcompose.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.demoappcompose.R
import com.example.demoappcompose.ui.VerticalSpacer
import com.example.demoappcompose.ui.components.Loader
import com.example.demoappcompose.ui.navigation.Screens
import com.example.demoappcompose.ui.theme.Blue
import com.example.demoappcompose.ui.theme.White
import com.example.demoappcompose.ui.theme.WhiteText
import com.example.demoappcompose.utility.UiState
import com.example.demoappcompose.utility.toast

@Composable
fun SplashScreen(
    navController: NavController,
    splashViewModel: SplashViewModel
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        val context = LocalContext.current

        Image(
            painter = painterResource(id = R.drawable.bg_img),
            contentDescription = "",
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop
        )

        val state by remember { splashViewModel.uiState }.collectAsStateWithLifecycle()
        when (state) {
            is UiState.Empty -> {}
            is UiState.Error -> {
                val errorMessage = (state as UiState.Error).data
                LaunchedEffect(Unit) {
                    context.toast(message = errorMessage)
                }
            }

            is UiState.Loading -> {
                Loader()
            }

            is UiState.Success -> {
                val configList = (state as UiState.Success).data.configList

                val orgName = configList.first { it.name == "APP_ORGANIZATION_NAME" }.value
                val tagline = configList.first { it.name == "APP_TAGLINE" }.value

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(all = 15.dp)
                ) {
                    VerticalSpacer(size = 20)
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    ) {
                        Text(
                            text = orgName,
                            style = TextStyle(
                                color = Color.White,
                                fontSize = 23.sp,
                                fontWeight = FontWeight.W700,
                                fontFamily = FontFamily(Font(R.font.quicksand_bold)),
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                        VerticalSpacer(size = 10)
                        Text(
                            text = tagline,
                            style = TextStyle(
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.W500,
                                fontFamily = FontFamily(Font(R.font.quicksand_medium)),
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    VerticalSpacer(size = 10)
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.login_img),
                            contentDescription = null,
                            modifier = Modifier
                                .width(130.dp)
                                .height(130.dp)
                        )
                    }
                    VerticalSpacer(size = 10)
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    ) {
                        Button(
                            onClick = {
                                navController.navigate(Screens.RegisterScreen.route)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Blue
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(45.dp)
                        ) {
                            Text(
                                text = "Sign Up",
                                style = TextStyle(
                                    color = Blue,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.W500,
                                    fontFamily = FontFamily(Font(R.font.quicksand_medium))
                                )
                            )
                        }

                        VerticalSpacer(size = 15)

                        Text(
                            text = "Already have an account?",
                            style = TextStyle(
                                color = WhiteText,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.W500,
                                fontFamily = FontFamily(Font(R.font.quicksand_medium)),
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        VerticalSpacer(size = 8)

                        Text(
                            text = "Login",
                            style = TextStyle(
                                color = White,
                                fontSize = 16.sp,
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

                        VerticalSpacer(size = 5)
                    }
                }
            }
        }
    }
}