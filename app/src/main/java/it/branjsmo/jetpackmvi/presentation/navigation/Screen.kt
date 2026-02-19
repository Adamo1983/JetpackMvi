package it.branjsmo.jetpackmvi.presentation.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Details : Screen("details/{postId}") {
        fun createRoute(postId: Int) = "details/$postId"
    }
    data object CreatePost : Screen("create_post")
    data object EditPost : Screen("edit_post/{postId}") {
        fun createRoute(postId: Int) = "edit_post/$postId"
    }
}
