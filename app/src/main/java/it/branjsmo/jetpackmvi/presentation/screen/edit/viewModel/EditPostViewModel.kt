package it.branjsmo.jetpackmvi.presentation.screen.edit.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.branjsmo.jetpackmvi.R
import it.branjsmo.jetpackmvi.domain.model.Post
import it.branjsmo.jetpackmvi.domain.usecase.GetPostDetailUseCase
import it.branjsmo.jetpackmvi.domain.usecase.UpdatePostUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditPostViewModel @Inject constructor(
    private val getPostDetailUseCase: GetPostDetailUseCase,
    private val updatePostUseCase: UpdatePostUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditPostUiState())
    val uiState = _uiState.asStateFlow()

    private val postId: Int? = savedStateHandle.get<Int>("postId")

    init {
        postId?.let { fetchPost(it) }
    }

    fun onAction(action: EditAction) {
        when (action) {
            is EditAction.OnTitleChange -> _uiState.value = _uiState.value.copy(title = action.title)
            is EditAction.OnBodyChange -> _uiState.value = _uiState.value.copy(body = action.body)
            is EditAction.OnThemeChange -> _uiState.value = _uiState.value.copy(theme = action.theme)
            is EditAction.OnSaveClick -> updatePost()
            else -> {}
        }
    }

    private fun fetchPost(id: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val post = getPostDetailUseCase(id)
            if (post != null) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    title = post.title,
                    body = post.body,
                    theme = post.theme,
                    userId = post.userId
                )
            } else {
                _uiState.value = _uiState.value.copy(isLoading = false, error = R.string.detail_error_not_found)
            }
        }
    }

    private fun updatePost() {
        val id = postId ?: return
        val title = _uiState.value.title
        val body = _uiState.value.body
        val theme = _uiState.value.theme

        if (title.isBlank() || body.isBlank()) {
            _uiState.value = _uiState.value.copy(error = R.string.error_empty_fields)
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            val result = updatePostUseCase(
                Post(
                    id = id,
                    userId = _uiState.value.userId,
                    title = title,
                    body = body,
                    theme = theme
                )
            )
            if (result != null) {
                _uiState.value = _uiState.value.copy(isLoading = false, isSuccess = true)
            } else {
                _uiState.value = _uiState.value.copy(isLoading = false, error = R.string.error_update_post)
            }
        }
    }
}
