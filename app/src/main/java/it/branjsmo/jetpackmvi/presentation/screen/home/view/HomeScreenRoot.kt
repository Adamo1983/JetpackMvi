package it.branjsmo.jetpackmvi.presentation.screen.home.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import it.branjsmo.jetpackmvi.presentation.screen.home.viewModel.HomeAction
import it.branjsmo.jetpackmvi.presentation.screen.home.viewModel.HomeViewModel

@Composable
fun HomeScreenRoot(
    onNavigateToDetail: (Int) -> Unit,
    onNavigateToCreate: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    HomeScreen(
        uiState = uiState,
        onAction = { action ->
            when (action) {
                is HomeAction.OnPostClick -> onNavigateToDetail(action.postId)
                is HomeAction.OnCreatePostClick -> onNavigateToCreate()
                else -> viewModel.onAction(action)
            }
        }
    )
}
