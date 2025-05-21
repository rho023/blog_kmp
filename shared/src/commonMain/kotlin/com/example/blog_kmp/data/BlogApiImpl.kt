package com.example.blog_kmp.data

import com.example.blog_kmp.domain.models.BlogApiModel
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class BlogApiImpl : BlogApi {
    private val client = HttpClient {
        install(ContentNegotiation.Plugin) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    override suspend fun getPosts(page: Int): List<BlogApiModel> {
        val url = "https://blog.vrid.in/wp-json/wp/v2/posts?per_page=10&page=$page"
        return client.get(url).body<List<BlogApiModel>>()
    }
}