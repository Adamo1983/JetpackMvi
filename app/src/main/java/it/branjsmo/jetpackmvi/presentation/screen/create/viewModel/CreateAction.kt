package it.branjsmo.jetpackmvi.presentation.screen.create.viewModel

import android.net.Uri
import it.branjsmo.jetpackmvi.domain.model.PostTheme

sealed class CreateAction {
    data object OnBackClick : CreateAction()
    data class OnTitleChange(val title: String) : CreateAction()
    data class OnBodyChange(val body: String) : CreateAction()
    data class OnImageSelected(val uri: Uri?) : CreateAction()
    data class OnThemeChange(val theme: PostTheme) : CreateAction()
    data object OnCreateClick : CreateAction()
}
