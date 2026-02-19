package it.branjsmo.jetpackmvi.presentation.screen.edit.viewModel

import it.branjsmo.jetpackmvi.domain.model.PostTheme

sealed class EditAction {
    data object OnBackClick : EditAction()
    data class OnTitleChange(val title: String) : EditAction()
    data class OnBodyChange(val body: String) : EditAction()
    data class OnThemeChange(val theme: PostTheme) : EditAction()
    data object OnSaveClick : EditAction()
}
