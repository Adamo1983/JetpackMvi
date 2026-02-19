package it.branjsmo.jetpackmvi.presentation.screen.home.viewModel

sealed class HomeAction {
    data class OnPostClick(val postId: Int) : HomeAction()
    data object OnCreatePostClick : HomeAction()
    data object Refresh : HomeAction()
}
