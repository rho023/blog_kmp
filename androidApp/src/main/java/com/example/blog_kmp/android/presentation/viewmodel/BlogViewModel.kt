package com.example.blog_kmp.android.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blog_kmp.android.domain.repository.BlogRepository
import com.example.blog_kmp.domain.models.BlogApiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BlogViewModel(
    private val repository: BlogRepository
) : ViewModel() {
    private val _blogs = MutableStateFlow<List<BlogApiModel>>(emptyList())
    val blogs: StateFlow<List<BlogApiModel>> = _blogs

    private val _isOnline = MutableStateFlow(repository.isOnline())
    val isOnline: StateFlow<Boolean> = _isOnline

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private var currentPage = 1

    init {
        loadNextPage()
    }

    fun loadNextPage() {
        viewModelScope.launch {
            _isLoading.value = true
            val newPosts = repository.getBlogs(currentPage++)
            _blogs.value = _blogs.value + newPosts
            _isLoading.value = false
        }
    }

    fun refreshConnectionState() {
        _isOnline.value = repository.isOnline()
    }
}