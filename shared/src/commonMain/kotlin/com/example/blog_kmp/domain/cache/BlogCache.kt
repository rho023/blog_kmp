package com.example.blog_kmp.domain.cache

import com.example.blog_kmp.domain.models.BlogApiModel

interface BlogCache {
    suspend fun save(posts: List<BlogApiModel>)
    suspend fun load(): List<BlogApiModel>
}