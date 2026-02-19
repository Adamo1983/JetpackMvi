package it.branjsmo.jetpackmvi.presentation.screen.create.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.branjsmo.jetpackmvi.R
import it.branjsmo.jetpackmvi.domain.model.Post
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

    fun onAction(action: CreateAction) {
        when (action) {
            is CreateAction.OnTitleChange -> _uiState.value = _uiState.value.copy(title = action.title)
            is CreateAction.OnBodyChange -> _uiState.value = _uiState.value.copy(body = action.body)
            is CreateAction.OnCreateClick -> createPost()
            else -> {}
        }
    }

    private fun createPost() {
        val title = _uiState.value.title
        val body = _uiState.value.body

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
                    body = body
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
