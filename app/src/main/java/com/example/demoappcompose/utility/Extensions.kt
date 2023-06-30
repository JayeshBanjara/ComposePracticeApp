package com.example.demoappcompose.utility

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

fun Context.sendMail(to: String = "example@gmail.com", subject: String = "Some subject") {
    try {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "vnd.android.cursor.item/email" // or "message/rfc822"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        toast(e.message.toString())
    } catch (t: Throwable) {
        toast(t.message.toString())
    }
}

fun Context.dial(phone: String) {
    try {
        val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))
        startActivity(intent)
    } catch (t: Throwable) {
        toast(t.message.toString())
    }
}

fun Context.openUrl(url: String) {
    try {
        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(webIntent)
    } catch (t: Throwable) {
        toast(t.message.toString())
    }
}

fun Context.toast(message: String?) {
    Toast.makeText(this, message ?: "Something went wrong", Toast.LENGTH_SHORT).show()
}