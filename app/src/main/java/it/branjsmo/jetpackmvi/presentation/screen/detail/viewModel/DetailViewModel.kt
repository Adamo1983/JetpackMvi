package it.branjsmo.jetpackmvi.presentation.screen.detail.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.branjsmo.jetpackmvi.R
import it.branjsmo.jetpackmvi.domain.usecase.DeletePostUseCase
import it.branjsmo.jetpackmvi.domain.usecase.GetPostDetailUseCase
import it.branjsmo.jetpackmvi.domain.repository.RepositoryImageProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getPostDetailUseCase: GetPostDetailUseCase,
    private val deletePostUseCase: DeletePostUseCase,
    private val imageProvider: RepositoryImageProvider,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState = _uiState.asStateFlow()

    private val postId: Int? = savedStateHandle.get<Int>("postId")

    init {
        postId?.let { fetchPostDetail(it) }
    }

    fun onAction(action: DetailAction, onBack: () -> Unit = {}) {
        when (action) {
            is DetailAction.OnDeleteClick -> deletePost(onBack)
            else -> {}
        }
    }

    private fun fetchPostDetail(id: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val post = getPostDetailUseCase(id)
            if (post != null) {
                val resolvedImageUrl = post.imageUrl
                    ?: imageProvider.getPostImageUrl(post.id, post.theme, 800, 600)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    post = post,
                    imageUrl = resolvedImageUrl
                )
            } else {
                _uiState.value = _uiState.value.copy(isLoading = false, error = R.string.detail_error_not_found)
            }
        }
    }

    private fun deletePost(onSuccess: () -> Unit) {
        val id = postId ?: return
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val success = deletePostUseCase(id)
            if (success) {
                onSuccess()
            } else {
                _uiState.value = _uiState.value.copy(isLoading = false, error = R.string.detail_error_delete)
            }
        }
    }
}
