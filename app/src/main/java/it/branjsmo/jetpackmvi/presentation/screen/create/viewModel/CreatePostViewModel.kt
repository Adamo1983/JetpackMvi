package it.branjsmo.jetpackmvi.presentation.screen.create.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.branjsmo.jetpackmvi.R
import it.branjsmo.jetpackmvi.domain.model.Post
import it.branjsmo.jetpackmvi.domain.model.PostTheme
import it.branjsmo.jetpackmvi.domain.usecase.CreatePostUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePostViewModel @Inject constructor(
    private val createPostUseCase: CreatePostUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreatePostUiState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.value = _uiState.value.copy(
            themeImageUrl = generateThemeImageUrl(_uiState.value.theme)
        )
    }

    fun onAction(action: CreateAction) {
        when (action) {
            is CreateAction.OnTitleChange -> _uiState.value = _uiState.value.copy(title = action.title)
            is CreateAction.OnBodyChange -> _uiState.value = _uiState.value.copy(body = action.body)
            is CreateAction.OnImageSelected -> _uiState.value = _uiState.value.copy(selectedImageUri = action.uri)
            is CreateAction.OnThemeChange -> _uiState.value = _uiState.value.copy(
                theme = action.theme,
                themeImageUrl = generateThemeImageUrl(action.theme)
            )
            is CreateAction.OnRefreshThemeImage -> _uiState.value = _uiState.value.copy(
                themeImageUrl = generateThemeImageUrl(_uiState.value.theme)
            )
            is CreateAction.OnCreateClick -> createPost()
            else -> {}
        }
    }

    private fun generateThemeImageUrl(theme: PostTheme): String {
        val seed = System.currentTimeMillis()
        return when (theme) {
            PostTheme.LANDSCAPE -> "https://picsum.photos/seed/$seed/800/600"
            PostTheme.ROBOT -> "https://robohash.org/$seed?size=800x600"
            PostTheme.AVATAR -> "https://i.pravatar.cc/800?u=$seed"
            PostTheme.TECH -> "https://loremflickr.com/800/600/technology?lock=$seed"
            PostTheme.KITTEN -> "https://loremflickr.com/800/600/kitten?lock=$seed"
            PostTheme.FOOD -> "https://loremflickr.com/800/600/food?lock=$seed"
            PostTheme.NATURE -> "https://loremflickr.com/800/600/nature?lock=$seed"
            PostTheme.BEARD -> "https://placebeard.it/800/600?random=$seed"
        }
    }

    private fun createPost() {
        val title = _uiState.value.title
        val body = _uiState.value.body
        val imageUri = _uiState.value.selectedImageUri
        val theme = _uiState.value.theme

        if (title.isBlank() || body.isBlank()) {
            _uiState.value = _uiState.value.copy(error = R.string.error_empty_fields)
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            val result = createPostUseCase(
                Post(
                    id = 0,
                    userId = 1,
                    title = title,
                    body = body,
                    theme = theme,
                    imageUrl = imageUri?.toString() ?: _uiState.value.themeImageUrl
                )
            )
            if (result != null) {
                _uiState.value = _uiState.value.copy(isLoading = false, isSuccess = true)
            } else {
                _uiState.value = _uiState.value.copy(isLoading = false, error = R.string.error_create_post)
            }
        }
    }
}
