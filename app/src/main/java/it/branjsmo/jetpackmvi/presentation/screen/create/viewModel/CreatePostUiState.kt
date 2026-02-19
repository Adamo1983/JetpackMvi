package it.branjsmo.jetpackmvi.presentation.screen.create.viewModel

import androidx.annotation.StringRes

data class CreatePostUiState(
    val title: String = "",
    val body: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    @get:StringRes val error: Int? = null
)
