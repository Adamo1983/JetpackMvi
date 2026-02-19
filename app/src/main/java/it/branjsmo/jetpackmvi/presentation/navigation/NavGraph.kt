package it.branjsmo.jetpackmvi.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import it.branjsmo.jetpackmvi.presentation.screen.create.view.CreatePostScreenRoot
import it.branjsmo.jetpackmvi.presentation.screen.detail.view.DetailScreenRoot
import it.branjsmo.jetpackmvi.presentation.screen.edit.view.EditPostScreenRoot
import it.branjsmo.jetpackmvi.presentation.screen.home.view.HomeScreenRoot

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreenRoot(
                onNavigateToDetail = { postId ->
                    navController.navigate(Screen.Details.createRoute(postId))
                },
                onNavigateToCreate = {
                    navController.navigate(Screen.CreatePost.route)
                }
            )
        }
        composable(
            route = Screen.Details.route,
            arguments = listOf(navArgument("postId") { type = NavType.IntType })
        ) {
            DetailScreenRoot(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToEdit = { postId ->
                    navController.navigate(Screen.EditPost.createRoute(postId))
                }
            )
        }
        composable(Screen.CreatePost.route) {
            CreatePostScreenRoot(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = Screen.EditPost.route,
            arguments = listOf(navArgument("postId") { type = NavType.IntType })
        ) {
            EditPostScreenRoot(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
