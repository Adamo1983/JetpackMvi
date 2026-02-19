package it.branjsmo.jetpackmvi.presentation.screen.detail.viewModel

sealed class DetailAction {
    data object OnBackClick : DetailAction()
    data class OnEditClick(val postId: Int) : DetailAction()
    data object OnDeleteClick : DetailAction()
}
