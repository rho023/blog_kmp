package com.example.blog_kmp.android.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import com.example.blog_kmp.android.presentation.viewmodel.BlogViewModel

@Composable
fun BlogListScreen(
    viewModel: BlogViewModel,
    onPostClick: (String) -> Unit
) {
    val isOnline by viewModel.isOnline.collectAsState()
    val blogList by viewModel.blogs.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val context = LocalContext.current

    var previousOnline by remember { mutableStateOf<Boolean?>(null) }

    LaunchedEffect(isOnline) {
        if (previousOnline != null && previousOnline != isOnline) {
            Toast.makeText(
                context,
                if (isOnline) "You are online" else "You are offline",
                Toast.LENGTH_SHORT
            ).show()
        } else if(previousOnline == null) {
            if(!isOnline) {
                Toast.makeText(
                    context,
                    "You are offline",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        previousOnline = isOnline
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp)
    ) {
        Column {
            if (!isOnline) {
                Button(
                    onClick = { viewModel.refreshConnectionState() },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        "Retry Connection",
                        fontSize = 12.sp,
                        modifier = Modifier.padding(0.dp)
                    )
                }
            }
            LazyColumn {
                items(items = blogList) { post ->
                    Text(
                        text = post.title.rendered,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onPostClick(post.link) }
                            .padding(16.dp)
                    )
                }
                item {
                    Button(
                        onClick = {
                            if (isOnline) viewModel.loadNextPage()
                            else Toast.makeText(context, "You are offline", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, bottom = 12.dp)
                    ) {
                        Text("Load More")
                    }
                }
            }
        }
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}