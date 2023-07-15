package com.example.demoappcompose.ui.create_question

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.compose.ui.graphics.Color
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.demoappcompose.R
import com.example.demoappcompose.utility.toast
import com.github.barteksc.pdfviewer.PDFView
import java.io.BufferedInputStream
import java.io.File
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class PDFViewerActivity : AppCompatActivity() {

    private lateinit var idPDFView: PDFView
    private lateinit var progressBar: ProgressBar
    private lateinit var clPdfRoot: ConstraintLayout
    private lateinit var btnDownload: Button
    private lateinit var downloadManager: DownloadManager
    private lateinit var pdfUrl: String

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdfviewer)

        pdfUrl = "https://ravieducation.com/public/paper_history/1689177716_paper.pdf"

        idPDFView = findViewById(R.id.idPDFView)
        progressBar = findViewById(R.id.progress_bar)
        clPdfRoot = findViewById(R.id.clPdfRoot)
        btnDownload = findViewById(R.id.btn_download)

        btnDownload.setOnClickListener {
            downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val uri = Uri.parse(pdfUrl)
            val request = DownloadManager.Request(uri)
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            downloadManager.enqueue(request)
        }

        loadPDFFromURL(pdfUrl = pdfUrl)
    }

    private fun loadPDFFromURL(pdfUrl: String) {
        idPDFView.let { RetrievePDFFromURL(it).execute(pdfUrl) }
    }

    inner class RetrievePDFFromURL(pdfView: PDFView) :
        AsyncTask<String, Void, InputStream>() {

        // on below line we are creating a variable for our pdf view.
        private val myPdfView: PDFView = pdfView

        override fun onPreExecute() {
            super.onPreExecute()
            progressBar.visibility = View.VISIBLE
        }

        // on below line we are calling our do in background method.
        override fun doInBackground(vararg params: String?): InputStream? {
            // on below line we are creating a variable for our input stream.
            var inputStream: InputStream? = null
            try {
                // on below line we are creating an url
                // for our url which we are passing as a string.
                val url = URL(params[0])

                val urlConnection = if (params.contains("https")) {
                    url.openConnection() as HttpsURLConnection
                } else {
                    url.openConnection() as HttpURLConnection
                }

                // on below line we are creating our http url connection.
                //   val urlConnection: HttpURLConnection = url.openConnection() as HttpsURLConnection

                // on below line we are checking if the response
                // is successful with the help of response code
                // 200 response code means response is successful
                if (urlConnection.responseCode == 200) {
                    // on below line we are initializing our input stream
                    // if the response is successful.
                    inputStream = BufferedInputStream(urlConnection.inputStream)
                }
            }
            // on below line we are adding catch block to handle exception
            catch (e: Exception) {
                // on below line we are simply printing
                // our exception and returning null
                e.printStackTrace()
                return null
            }
            // on below line we are returning input stream.
            return inputStream
        }

        // on below line we are calling on post execute
        // method to load the url in our pdf view.
        override fun onPostExecute(result: InputStream?) {
            // on below line we are loading url within our
            // pdf view on below line using input stream.

            val pdfView =
                myPdfView.fromStream(result).spacing(10).autoSpacing(false).enableAntialiasing(true)
                    .onError {
                        progressBar.visibility = View.GONE
                        toast("Error loading PDF")
                        finish()
                    }.onLoad {
                        progressBar.visibility = View.GONE
                        /*clPdfRoot.setBackgroundColor(
                            ContextCompat.getColor(
                                this@PDFViewerActivity, 0xFFC4C4C4
                            )
                        )
                        myPdfView.setBackgroundColor(
                            ContextCompat.getColor(
                                this@PDFViewerActivity, R.color.ed_boarder_color
                            )
                        )*/
                    }

            pdfView.load()

        }
    }

}