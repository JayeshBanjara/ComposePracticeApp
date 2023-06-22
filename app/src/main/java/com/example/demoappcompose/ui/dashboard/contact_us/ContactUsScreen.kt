package com.example.demoappcompose.ui.dashboard.contact_us

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
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
        modifier = Modifier
            .fillMaxSize()
    ) {

        var isWebViewLoading by remember { mutableStateOf(false) }

        Image(
            painter = painterResource(id = R.drawable.screen_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

        Card(
            modifier = modifier
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

                VerticalSpacer(size = 100)

                val htmlContent = "<html><body><iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3670.933452335579!2d72.62607747423534!3d23.062901214824166!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x395e87b26ce31acb%3A0x716c6c11fa3d9ccc!2sHarsh%20stationary%20%26%20xerox!5e0!3m2!1sen!2sin!4v1687071578421!5m2!1sen!2sin\" width=\"700\" height=\"450\" style=\"border:0;\" allowfullscreen=\"\" loading=\"lazy\" referrerpolicy=\"no-referrer-when-downgrade\"></iframe></body></html>"

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    AndroidView(factory = { context ->
                        WebView(context).apply {
                            webViewClient = object : WebViewClient() {

                                override fun onPageStarted(
                                    view: WebView?,
                                    url: String?,
                                    favicon: Bitmap?
                                ) {
                                    super.onPageStarted(view, url, favicon)
                                    isWebViewLoading = true
                                }

                                override fun onPageFinished(view: WebView?, url: String?) {
                                    super.onPageFinished(view, url)
                                    isWebViewLoading = false
                                }
                            }
                            settings.javaScriptEnabled = true
                            loadDataWithBaseURL(
                                null,
                                htmlContent,
                                "text/html",
                                "UTF-8",
                                null
                            )
                        }
                    })
                    if (isWebViewLoading) {
                        CircularProgressIndicator()
                    }
                }

            }
        }
    }
}