package com.example.blog_kmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform