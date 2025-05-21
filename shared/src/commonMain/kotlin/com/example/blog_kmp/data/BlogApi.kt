package com.example.blog_kmp.data

import com.example.blog_kmp.domain.models.BlogApiModel

interface BlogApi {
    suspend fun getPosts(page: Int): List<BlogApiModel>
}