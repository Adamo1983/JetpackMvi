package it.branjsmo.jetpackmvi.presentation.screen.create.viewModel

sealed class CreateAction {
    data object OnBackClick : CreateAction()
    data class OnTitleChange(val title: String) : CreateAction()
    data class OnBodyChange(val body: String) : CreateAction()
    data object OnCreateClick : CreateAction()
}
