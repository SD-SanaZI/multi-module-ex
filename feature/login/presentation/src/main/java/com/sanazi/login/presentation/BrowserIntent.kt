package com.sanazi.login.presentation

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri

class BrowserIntent(private val context: Context){
    fun openUrlInBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = url.toUri()
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }
}