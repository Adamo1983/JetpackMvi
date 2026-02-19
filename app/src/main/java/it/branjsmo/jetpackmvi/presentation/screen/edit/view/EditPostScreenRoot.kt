package it.branjsmo.jetpackmvi.presentation.screen.edit.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import it.branjsmo.jetpackmvi.presentation.screen.edit.viewModel.EditAction
import it.branjsmo.jetpackmvi.presentation.screen.edit.viewModel.EditPostViewModel

@Composable
fun EditPostScreenRoot(
    onNavigateBack: () -> Unit,
    viewModel: EditPostViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            onNavigateBack()
        }
    }

    EditPostScreen(
        uiState = uiState,
        onAction = { action ->
            when (action) {
                is EditAction.OnBackClick -> onNavigateBack()
                else -> viewModel.onAction(action)
            }
        }
    )
}
