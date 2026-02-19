package it.branjsmo.jetpackmvi.presentation.screen.detail.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import it.branjsmo.jetpackmvi.presentation.screen.detail.viewModel.DetailAction
import it.branjsmo.jetpackmvi.presentation.screen.detail.viewModel.DetailViewModel

@Composable
fun DetailScreenRoot(
    onNavigateBack: () -> Unit,
    onNavigateToEdit: (Int) -> Unit,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    DetailScreen(
        uiState = uiState,
        onAction = { action ->
            when (action) {
                is DetailAction.OnBackClick -> onNavigateBack()
                is DetailAction.OnEditClick -> onNavigateToEdit(action.postId)
                is DetailAction.OnDeleteClick -> viewModel.onAction(action, onBack = onNavigateBack)
            }
        }
    )
}
