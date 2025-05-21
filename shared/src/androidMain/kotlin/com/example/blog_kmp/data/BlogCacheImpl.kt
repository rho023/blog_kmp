package com.example.blog_kmp.data

import android.content.Context
import com.example.blog_kmp.domain.cache.BlogCache
import com.example.blog_kmp.domain.models.BlogApiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import java.io.File

class BlogCacheImpl(private val context: Context): BlogCache {
    private val fileName = "blog_cache.json"

    override suspend fun save(posts: List<BlogApiModel>) {
        val json = Json.Default.encodeToString(ListSerializer(BlogApiModel.serializer()), posts)
        withContext(Dispatchers.IO) {
            context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
                it.write(json.toByteArray())
            }
        }
    }

    override suspend fun load(): List<BlogApiModel> {
        return withContext(Dispatchers.IO) {
            val file = File(context.filesDir, fileName)
            if (!file.exists()) return@withContext emptyList()
            val json = file.readText()
            Json.Default.decodeFromString(json)
        }
    }
}