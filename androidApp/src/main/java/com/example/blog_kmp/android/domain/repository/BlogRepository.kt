package com.example.blog_kmp.android.domain.repository

import android.content.Context
import com.example.blog_kmp.domain.cache.BlogCache
import com.example.blog_kmp.android.domain.network.NetworkMonitor
import com.example.blog_kmp.data.BlogApi
import com.example.blog_kmp.domain.models.BlogApiModel

class BlogRepository(
    private val context: Context,
    private val api: BlogApi,
    private val cache: BlogCache,
    private val monitor: NetworkMonitor
) {
    suspend fun getBlogs(page: Int): List<BlogApiModel> {
        return try {
            if (monitor.isOnline(context)) {
                val posts = api.getPosts(page)
                if (page == 1) cache.save(posts) // cache first page
                posts
            } else {
                val cached = cache.load()
                cached
            }
        } catch (e: Exception) {
            val fallback = cache.load()
            fallback
        }
    }

    fun isOnline(): Boolean = monitor.isOnline(context)
}