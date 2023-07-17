package com.example.demoappcompose.ui.create_question

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.demoappcompose.R
import com.example.demoappcompose.data.responses.question_list.QuestionData
import com.example.demoappcompose.ui.HorizontalSpacer
import com.example.demoappcompose.ui.VerticalSpacer
import com.example.demoappcompose.ui.components.CustomTopAppBar
import com.example.demoappcompose.ui.components.Loader
import com.example.demoappcompose.ui.components.ScreenBackground
import com.example.demoappcompose.ui.create_question.model.Section
import com.example.demoappcompose.ui.navigation.Screens
import com.example.demoappcompose.ui.popUpToTop
import com.example.demoappcompose.ui.screenPadding
import com.example.demoappcompose.ui.theme.AppRed
import com.example.demoappcompose.ui.theme.Blue
import com.example.demoappcompose.ui.theme.LightBlue
import com.example.demoappcompose.ui.theme.NoRippleTheme
import com.example.demoappcompose.ui.theme.TitleColor
import com.example.demoappcompose.utility.Constants
import com.example.demoappcompose.utility.UiState
import com.example.demoappcompose.utility.toast
import com.google.gson.Gson
import kotlinx.coroutines.launch

@Composable
fun ChapterList(
    navController: NavController,
    chapterListViewModel: ChapterListViewModel,
    classId: String,
    subjectId: String,
    subjectName: String,
    section: String,
    updatedSection: String?
) {

    BackHandler {
        navController.previousBackStackEntry
            ?.savedStateHandle
            ?.set(Constants.QUESTIONS, updatedSection)
        navController.popBackStack()
    }

    var questions = mutableListOf<QuestionData>()

        LaunchedEffect(updatedSection) {
            val x = Gson().fromJson(updatedSection ?: section,  Section::class.java)
            if(x != null) {
                questions = x.questions!!
            }
    }


    Scaffold(topBar = {
        CustomTopAppBar(
            title = subjectName,
            showBack = true,
            onBackClick = {
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set(Constants.QUESTIONS, updatedSection)
                navController.popBackStack()
            },

            questionCounts = questions?.size ?: 0
        )
    }) { innerPadding ->

        var showQRView by remember { mutableStateOf(false) }
        var clickedPos by remember { mutableIntStateOf(0) }

        val context = LocalContext.current
        val coroutineScope = rememberCoroutineScope()

        CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
            Box(modifier = Modifier.fillMaxSize()) {

                ScreenBackground()

                LaunchedEffect(Unit) {
                    coroutineScope.launch {
                        chapterListViewModel.getChapterList(
                            classId = classId, subjectId = subjectId
                        )
                    }
                }

                val state by remember { chapterListViewModel.uiState }.collectAsStateWithLifecycle()
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

                        val chapterList = (state as UiState.Success).data.chapterData.chapterList

                        Column(
                            modifier = Modifier.padding(
                                start = screenPadding(),
                                top = innerPadding.calculateTopPadding(),
                                end = screenPadding(),
                                bottom = screenPadding()
                            )
                        ) {

                            VerticalSpacer(size = 10)

                            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp),
                                content = {
                                    itemsIndexed(chapterList) { index, chapter ->
                                        Column(modifier = Modifier
                                            .fillMaxWidth()
                                            .border(
                                                BorderStroke(width = 1.dp, color = Blue),
                                                shape = RoundedCornerShape(10.dp)
                                            )
                                            .background(
                                                color = LightBlue,
                                                shape = RoundedCornerShape(10.dp)
                                            )
                                            .padding(10.dp)
                                            .clickable {
                                                if (chapter.qrCodeData.isEmpty()) {
                                                    navController.navigate(
                                                        Screens.QuestionList.withArgs(
                                                            chapter.chapterName,
                                                            chapter.classId,
                                                            chapter.subjectId,
                                                            chapter.chpId,
                                                            section
                                                        )
                                                    )
                                                } else {
                                                    showQRView = showQRView.not()
                                                    clickedPos = index
                                                }
                                            }) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Column(
                                                    modifier = Modifier.weight(1f),
                                                    horizontalAlignment = Alignment.CenterHorizontally
                                                ) {
                                                    VerticalSpacer(size = 5)
                                                    Text(
                                                        text = chapter.chapterTitle,
                                                        style = TextStyle(
                                                            color = Blue,
                                                            fontSize = 16.sp,
                                                            fontWeight = FontWeight.W700,
                                                            fontFamily = FontFamily(Font(R.font.quicksand_bold)),
                                                            textAlign = TextAlign.Center
                                                        )
                                                    )

                                                    VerticalSpacer(size = 5)

                                                    Text(
                                                        text = chapter.chapterName,
                                                        style = TextStyle(
                                                            color = Blue,
                                                            fontSize = 16.sp,
                                                            fontWeight = FontWeight.W700,
                                                            fontFamily = FontFamily(Font(R.font.quicksand_semi_bold)),
                                                            textAlign = TextAlign.Center
                                                        )
                                                    )

                                                    VerticalSpacer(size = 5)
                                                }

                                                if (chapter.qrCodeData.isNotEmpty()) {
                                                    HorizontalSpacer(size = 5)
                                                }
                                                if (chapter.qrCodeData.isNotEmpty()) {
                                                    IconButton(onClick = {
                                                        showQRView = showQRView.not()
                                                        clickedPos = index

                                                        if(showQRView) {
                                                            coroutineScope.launch {
                                                                chapterListViewModel.generaTePaymentRequest(
                                                                    classId = classId,
                                                                    subjectId = subjectId,
                                                                    amount = chapter.qrCodeData.first { it.name == "FINAL_PRICE" }.value
                                                                )
                                                            }
                                                        }

                                                    }) {
                                                        Icon(
                                                            imageVector = Icons.Filled.Lock,
                                                            contentDescription = null,
                                                            tint = Blue,
                                                            modifier = Modifier.size(50.dp)
                                                        )
                                                    }
                                                }
                                            }

                                            VerticalSpacer(size = 10)

                                            AnimatedVisibility(visible = (chapter.qrCodeData.isNotEmpty()) and (clickedPos == index) and showQRView) {
                                                Column(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalAlignment = Alignment.CenterHorizontally
                                                ) {

                                                    val qrCodeImage =
                                                        chapter.qrCodeData.find { it.name == "QR_CODE" }

                                                    if (qrCodeImage != null) {
                                                        VerticalSpacer(size = 10)
                                                    }

                                                    if (qrCodeImage != null) {
                                                        AsyncImage(
                                                            model = qrCodeImage.value,
                                                            contentDescription = "QR Code",
                                                            contentScale = ContentScale.Fit,
                                                            modifier = Modifier.size(200.dp)
                                                        )
                                                    }

                                                    VerticalSpacer(size = 10)

                                                    Text(
                                                        text = "Total You Pay", style = TextStyle(
                                                            color = TitleColor,
                                                            fontSize = 15.sp,
                                                            fontWeight = FontWeight.W500,
                                                            fontFamily = FontFamily(Font(R.font.quicksand_semi_bold))
                                                        )
                                                    )

                                                    Row(
                                                        horizontalArrangement = Arrangement.Center,
                                                        verticalAlignment = Alignment.CenterVertically
                                                    ) {
                                                        Text(
                                                            text = "₹ ${chapter.qrCodeData.first { it.name == "FINAL_PRICE" }.value}",
                                                            style = TextStyle(
                                                                color = Blue,
                                                                fontSize = 22.sp,
                                                                fontWeight = FontWeight.W700,
                                                                fontFamily = FontFamily(Font(R.font.quicksand_bold))
                                                            )
                                                        )

                                                        HorizontalSpacer(size = 5)

                                                        Text(
                                                            text = chapter.qrCodeData.first { it.name == "CUT_PRICE" }.value,
                                                            style = TextStyle(
                                                                color = Color.DarkGray,
                                                                fontSize = 16.sp,
                                                                fontWeight = FontWeight.W500,
                                                                fontFamily = FontFamily(Font(R.font.quicksand_semi_bold)),
                                                                textDecoration = TextDecoration.LineThrough
                                                            )
                                                        )
                                                    }

                                                    VerticalSpacer(size = 5)

                                                    Column(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .padding(horizontal = 20.dp),
                                                        horizontalAlignment = Alignment.Start
                                                    ) {

                                                        if (chapter.qrCodeData.find { it.name == "ONLINE_PAYMENT_VALIDITY" } != null) PaymentInstructionText(
                                                            "Online Payment (Validity: ${chapter.qrCodeData.first { it.name == "ONLINE_PAYMENT_VALIDITY" }.value})"
                                                        )

                                                        if (chapter.qrCodeData.find { it.name == "PAYTM_NUMBER" } != null) PaymentInstructionText(
                                                            "Paytm Number: ${chapter.qrCodeData.first { it.name == "PAYTM_NUMBER" }.value}"
                                                        )

                                                        if (chapter.qrCodeData.find { it.name == "PHONEPE_NUMBER" } != null) PaymentInstructionText(
                                                            "PhonePe Number: ${chapter.qrCodeData.first { it.name == "PHONEPE_NUMBER" }.value}"
                                                        )

                                                        if (chapter.qrCodeData.find { it.name == "GOOGLE_PAY_NUMBER" } != null) PaymentInstructionText(
                                                            "Google Pay Number: ${chapter.qrCodeData.first { it.name == "GOOGLE_PAY_NUMBER" }.value}"
                                                        )

                                                        if (chapter.qrCodeData.find { it.name == "BHIM_UPI" } != null) PaymentInstructionText(
                                                            "BHIM UPI: ${chapter.qrCodeData.first { it.name == "BHIM_UPI" }.value}"
                                                        )

                                                        PaymentInstructionText("Bank Details:")
                                                        if (chapter.qrCodeData.find { it.name == "ACCOUNT_HOLDER_NAME" } != null) PaymentInstructionText(
                                                            "Account Holder Name: ${chapter.qrCodeData.first { it.name == "ACCOUNT_HOLDER_NAME" }.value}"
                                                        )

                                                        if (chapter.qrCodeData.find { it.name == "ACCOUNT_NUMBER" } != null) PaymentInstructionText(
                                                            "Account Number: ${chapter.qrCodeData.first { it.name == "ACCOUNT_NUMBER" }.value}"
                                                        )

                                                        if (chapter.qrCodeData.find { it.name == "IFSC_CODE" } != null) PaymentInstructionText(
                                                            "IFSC Code: ${chapter.qrCodeData.first { it.name == "IFSC_CODE" }.value}"
                                                        )

                                                        if (chapter.qrCodeData.find { it.name == "BRANCH_NAME" } != null) PaymentInstructionText(
                                                            "BRANCH: ${chapter.qrCodeData.first { it.name == "BRANCH_NAME" }.value}"
                                                        )

                                                        if (chapter.qrCodeData.find { it.name == "PAYMENT_RELATED_NOTE" } != null) VerticalSpacer(
                                                            size = 5
                                                        )

                                                        if (chapter.qrCodeData.find { it.name == "PAYMENT_RELATED_NOTE" } != null) PaymentInstructionText(
                                                            chapter.qrCodeData.first { it.name == "PAYMENT_RELATED_NOTE" }.value
                                                        )

                                                        if (chapter.qrCodeData.find { it.name == "PAYMENT_RELATED_ISSUE_NOTE" } != null) VerticalSpacer(
                                                            size = 20
                                                        )

                                                        if (chapter.qrCodeData.find { it.name == "PAYMENT_RELATED_ISSUE_NOTE" } != null) Text(
                                                            text = chapter.qrCodeData.first { it.name == "PAYMENT_RELATED_ISSUE_NOTE" }.value,
                                                            style = TextStyle(
                                                                color = Color.DarkGray,
                                                                fontSize = 12.sp,
                                                                fontWeight = FontWeight.W500,
                                                                fontFamily = FontFamily(Font(R.font.quicksand_medium))
                                                            )
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PaymentInstructionText(text: String) {
    Text(
        text = text, style = TextStyle(
            color = AppRed,
            fontSize = 12.sp,
            fontWeight = FontWeight.W500,
            fontFamily = FontFamily(Font(R.font.quicksand_medium))
        )
    )
}