package com.example.blog_kmp.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.blog_kmp.data.BlogCacheImpl
import com.example.blog_kmp.android.data.network.NetworkMonitorImpl
import com.example.blog_kmp.android.domain.network.NetworkMonitor
import com.example.blog_kmp.android.domain.repository.BlogRepository
import com.example.blog_kmp.data.BlogApiImpl
import com.example.blog_kmp.android.navigation.BlogApp
import com.example.blog_kmp.android.presentation.viewmodel.BlogViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: BlogViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val context = applicationContext

                val api = BlogApiImpl()
                val cache = BlogCacheImpl(context)
                val networkMonitor: NetworkMonitor = NetworkMonitorImpl()

                val repository = BlogRepository(context, api, cache, networkMonitor)

                @Suppress("UNCHECKED_CAST")
                return BlogViewModel(repository) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BlogApp(viewModel)
                }
            }
        }
    }
}
