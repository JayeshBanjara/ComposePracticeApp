package com.example.demoappcompose.ui.dashboard.contact_us

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.demoappcompose.R
import com.example.demoappcompose.ui.VerticalSpacer
import com.example.demoappcompose.ui.screenPadding
import com.example.demoappcompose.utility.dial
import com.example.demoappcompose.utility.openUrl

@Composable
fun ContactUsScreen(navController: NavController, modifier: Modifier) {

    val context = LocalContext.current
    // Create the launcher for the activity result contract
    val sendEmailLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        // Handle the result of the email sending process if needed
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding()
    ) {
        Card(
            modifier = Modifier
                .padding(screenPadding())
                .fillMaxWidth()
                .wrapContentHeight(),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(screenPadding())
            ) {
                ContactUsRow(
                    headerText = "Website",
                    valueText = "www.google.com"
                ) {
                    context.openUrl(url = "https://www.google.com/")
                }
                VerticalSpacer(size = 15)
                ContactUsRow(
                    headerText = "Email",
                    valueText = "example@gmail.com"
                ) {
                    val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:example@gmail.com")
                        putExtra(Intent.EXTRA_SUBJECT, "Hello!")
                        putExtra(Intent.EXTRA_TEXT, "This is the email body.")
                    }

                    // Create a custom chooser dialog
                    val chooserIntent = Intent.createChooser(emailIntent, "Send Email")

                    // Launch the chooser dialog via the activity result launcher
                    sendEmailLauncher.launch(chooserIntent)
                }
                VerticalSpacer(size = 15)
                ContactUsRow(
                    headerText = "Mobile",
                    valueText = "+91 9106691910"
                ) {
                    context.dial(phone = "+919106691910")
                }

                VerticalSpacer(size = 15)
            }
        }
    }
}