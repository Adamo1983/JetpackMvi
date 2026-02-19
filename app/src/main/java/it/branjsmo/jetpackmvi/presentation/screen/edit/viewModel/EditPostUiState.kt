package it.branjsmo.jetpackmvi.presentation.screen.edit.viewModel

import androidx.annotation.StringRes
import it.branjsmo.jetpackmvi.domain.model.PostTheme

data class EditPostUiState(
    val title: String = "",
    val body: String = "",
    val theme: PostTheme = PostTheme.LANDSCAPE,
    val userId: Int = 0,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    @get:StringRes val error: Int? = null
)
