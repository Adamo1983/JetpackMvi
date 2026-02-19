package it.branjsmo.jetpackmvi.presentation.screen.home.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.branjsmo.jetpackmvi.R
import it.branjsmo.jetpackmvi.domain.usecase.GetPostsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchPosts()
    }

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.Refresh -> fetchPosts()
            else -> {} // Altre azioni gestite dalla Root
        }
    }

    private fun fetchPosts() {
        viewModelScope.launch {
            getPostsUseCase()
                .onStart { _uiState.value = _uiState.value.copy(isLoading = true) }
                .catch { _uiState.value = _uiState.value.copy(isLoading = false, error = R.string.home_error_generic) }
                .collect { posts ->
                    _uiState.value = _uiState.value.copy(isLoading = false, posts = posts)
                }
        }
    }
}
