package com.example.demoappcompose.ui.create_question

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.demoappcompose.R
import com.example.demoappcompose.ui.HorizontalSpacer
import com.example.demoappcompose.ui.VerticalSpacer
import com.example.demoappcompose.ui.components.CustomTopAppBar
import com.example.demoappcompose.ui.create_question.model.Chapter
import com.example.demoappcompose.ui.navigation.Screens
import com.example.demoappcompose.ui.screenPadding
import com.example.demoappcompose.ui.theme.AppRed
import com.example.demoappcompose.ui.theme.Blue
import com.example.demoappcompose.ui.theme.LightBlue
import com.example.demoappcompose.ui.theme.NoRippleTheme
import com.example.demoappcompose.ui.theme.TitleColor

@Composable
fun ChapterList(navController: NavController, subjectName: String) {

    val list = mutableListOf<Chapter>()
    for (i in 1..10) {
        if (i == 1 || i == 2) {
            list.add(Chapter(isLocked = false))
        } else {
            list.add(Chapter(isLocked = true))
        }
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = subjectName,
                showBack = true,
                onBackClick = {
                    navController.popBackStack()
                },
                questionCounts = 5
            )
        }
    ) { innerPadding ->

        var showQRView by remember { mutableStateOf(false) }
        var clickedPos by remember { mutableIntStateOf(0) }

        CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
            Box(modifier = Modifier.fillMaxSize()) {

                Image(
                    painter = painterResource(id = R.drawable.screen_bg),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )

                Column(
                    modifier = Modifier.padding(
                        start = screenPadding(),
                        top = innerPadding.calculateTopPadding(),
                        end = screenPadding(),
                        bottom = screenPadding()
                    )
                ) {

                    VerticalSpacer(size = 10)

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        content = {
                            itemsIndexed(list) { index, item ->
                                Column(
                                    modifier = Modifier
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
                                            if (index < 2) {
                                                navController.navigate(
                                                    Screens.QuestionList.withArgs(
                                                        "अध्याय ${index + 1} का नाम"
                                                    )
                                                )
                                            } else {
                                                showQRView = showQRView.not()
                                                clickedPos = index
                                            }
                                        }
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .weight(1f),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            VerticalSpacer(size = 5)
                                            Text(
                                                text = "अध्याय ${index + 1}",
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
                                                text = "अध्याय ${index + 1} का नाम",
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

                                        if (index > 1) {
                                            HorizontalSpacer(size = 5)
                                        }
                                        if (index > 1) {
                                            IconButton(onClick = {
                                                showQRView = showQRView.not()
                                                clickedPos = index
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

                                    AnimatedVisibility(visible = item.isLocked && clickedPos == index && showQRView) {
                                        Column(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            VerticalSpacer(size = 10)

                                            Image(
                                                painter = painterResource(id = R.drawable.ic_qr),
                                                contentDescription = null
                                            )

                                            VerticalSpacer(size = 10)

                                            Text(
                                                text = "Total You Pay",
                                                style = TextStyle(
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
                                                    text = "₹ 300",
                                                    style = TextStyle(
                                                        color = Blue,
                                                        fontSize = 22.sp,
                                                        fontWeight = FontWeight.W700,
                                                        fontFamily = FontFamily(Font(R.font.quicksand_bold))
                                                    )
                                                )

                                                HorizontalSpacer(size = 5)

                                                Text(
                                                    text = "700",
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
                                                PaymentInstructionText("Online Payment (Validity: 30/04/2024)")
                                                PaymentInstructionText("Paytm Number: 1234567890")
                                                PaymentInstructionText("PhonePe Number: 1234567890")
                                                PaymentInstructionText("Google Pay Number: 1234567890")
                                                PaymentInstructionText("BHIM UPI: 1234567890@upi")
                                                PaymentInstructionText("Bank Details:")
                                                PaymentInstructionText("Account Holder Name: Ravi Education")
                                                PaymentInstructionText("Account Number: 3194123859674515")
                                                PaymentInstructionText("IFSC Code: UTIX00000015")
                                                PaymentInstructionText("BRANCH: Axis Bank - Naroda, Ahmedabad")
                                                VerticalSpacer(size = 5)
                                                PaymentInstructionText("જે વિષય કે બુક માટે પેમેન્ટ કર્યું હોય તેનો સ્ક્રીન શોટ પાડી 1234567890 / 1234567890 નંબર પર WhatsApp કરી દેવું.")
                                                VerticalSpacer(size = 20)
                                                Text(
                                                    text = "Payment ને આધારિત કોઈ Help માટે +91 12345 67890 પર સંપર્ક કરવો. (9:00 AM - 8:00 PM)",
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

@Composable
fun PaymentInstructionText(text: String) {
    Text(
        text = text,
        style = TextStyle(
            color = AppRed,
            fontSize = 12.sp,
            fontWeight = FontWeight.W500,
            fontFamily = FontFamily(Font(R.font.quicksand_medium))
        )
    )
}