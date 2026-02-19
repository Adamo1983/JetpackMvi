package it.branjsmo.jetpackmvi.presentation.screen.home.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import it.branjsmo.jetpackmvi.R
import it.branjsmo.jetpackmvi.domain.model.Post
import it.branjsmo.jetpackmvi.presentation.components.LoadingAnimation
import it.branjsmo.jetpackmvi.presentation.screen.home.view.components.PostItem
import it.branjsmo.jetpackmvi.presentation.screen.home.viewModel.HomeAction
import it.branjsmo.jetpackmvi.presentation.screen.home.viewModel.HomeUiState
import it.branjsmo.jetpackmvi.presentation.theme.JetpackMviTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onAction: (HomeAction) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.home_title), fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onAction(HomeAction.OnCreatePostClick) }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.home_add_post)
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (uiState.isLoading) {
                LoadingAnimation(modifier = Modifier.align(Alignment.Center))
            } else if (uiState.error != null) {
                Text(
                    text = stringResource(uiState.error),
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(uiState.posts) { post ->
                        PostItem(
                            post = post,
                            onClick = { onAction(HomeAction.OnPostClick(post.id)) }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    JetpackMviTheme {
        HomeScreen(
            uiState = HomeUiState(
                posts = listOf(
                    Post(1, 1, "Post 1", "Body 1"),
                    Post(2, 1, "Post 2", "Body 2")
                )
            ),
            onAction = {}
        )
    }
}
