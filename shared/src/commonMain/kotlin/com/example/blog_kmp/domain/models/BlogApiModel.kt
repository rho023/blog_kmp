package com.example.blog_kmp.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class BlogApiModel(
    val id: Int,
    val title: Rendered,
    val link: String
)

@Serializable
data class Rendered(
    val rendered: String
)