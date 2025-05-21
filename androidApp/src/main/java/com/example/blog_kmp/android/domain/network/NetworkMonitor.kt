package com.example.blog_kmp.android.domain.network

import android.content.Context

interface NetworkMonitor {
    fun isOnline(context: Context): Boolean
}