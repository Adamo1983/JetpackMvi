package it.branjsmo.jetpackmvi.presentation.screen.detail.viewModel

import androidx.annotation.StringRes
import it.branjsmo.jetpackmvi.domain.model.Post

data class DetailUiState(
    val post: Post? = null,
    val imageUrl: String? = null,
    val isLoading: Boolean = false,
    @get:StringRes val error: Int? = null
)
