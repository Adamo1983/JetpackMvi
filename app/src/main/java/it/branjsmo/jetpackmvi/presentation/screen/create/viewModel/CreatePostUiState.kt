package it.branjsmo.jetpackmvi.presentation.screen.create.viewModel

import android.net.Uri
import androidx.annotation.StringRes

data class CreatePostUiState(
    val title: String = "",
    val body: String = "",
    val selectedImageUri: Uri? = null,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    @get:StringRes val error: Int? = null
)
