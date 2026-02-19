package it.branjsmo.jetpackmvi.presentation.screen.create.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import it.branjsmo.jetpackmvi.presentation.screen.create.viewModel.CreateAction
import it.branjsmo.jetpackmvi.presentation.screen.create.viewModel.CreatePostViewModel

@Composable
fun CreatePostScreenRoot(
    onNavigateBack: () -> Unit,
    viewModel: CreatePostViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            onNavigateBack()
        }
    }

    CreatePostScreen(
        uiState = uiState,
        onAction = { action ->
            when (action) {
                is CreateAction.OnBackClick -> onNavigateBack()
                else -> viewModel.onAction(action)
            }
        }
    )
}
