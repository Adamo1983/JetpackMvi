package it.branjsmo.jetpackmvi.presentation.screen.home.viewModel

import androidx.annotation.StringRes
import it.branjsmo.jetpackmvi.domain.model.Post

data class HomeUiState(
    val posts: List<Post> = emptyList(),
    val isLoading: Boolean = false,
    @get:StringRes val error: Int? = null
)
