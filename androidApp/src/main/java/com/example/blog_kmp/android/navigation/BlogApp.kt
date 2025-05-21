package com.example.blog_kmp.android.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.blog_kmp.android.presentation.screens.BlogDetailScreen
import com.example.blog_kmp.android.presentation.screens.BlogListScreen
import com.example.blog_kmp.android.presentation.viewmodel.BlogViewModel
import java.net.URLDecoder
import java.net.URLEncoder

@Composable
fun BlogApp(viewModel: BlogViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            BlogListScreen(viewModel = viewModel, onPostClick = { link ->
                navController.navigate("detail/${URLEncoder.encode(link, "UTF-8")}")
            })
        }

        composable(
            "detail/{link}",
            arguments = listOf(navArgument("link") { type = NavType.StringType })
        ){ backStackEntry ->
            val link = backStackEntry.arguments?.getString("link")?.let {
                URLDecoder.decode(it, "UTF-8")
            } ?: ""
            BlogDetailScreen(link = link)
        }
    }
}