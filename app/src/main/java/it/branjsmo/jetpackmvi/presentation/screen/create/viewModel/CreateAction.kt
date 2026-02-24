package it.branjsmo.jetpackmvi.presentation.screen.create.viewModel

import android.net.Uri

sealed class CreateAction {
    data object OnBackClick : CreateAction()
    data class OnTitleChange(val title: String) : CreateAction()
    data class OnBodyChange(val body: String) : CreateAction()
    data class OnImageSelected(val uri: Uri?) : CreateAction()
    data object OnCreateClick : CreateAction()
}
