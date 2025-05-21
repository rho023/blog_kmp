package com.example.blog_kmp.android.presentation.screens

import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun BlogDetailScreen(link: String) {
    AndroidView(factory = {
        WebView(it).apply {
            loadUrl(link)
        }
    })
}