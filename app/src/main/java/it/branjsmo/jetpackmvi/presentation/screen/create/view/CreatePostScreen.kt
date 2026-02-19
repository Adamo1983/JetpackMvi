package it.branjsmo.jetpackmvi.presentation.screen.create.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import it.branjsmo.jetpackmvi.R
import it.branjsmo.jetpackmvi.presentation.components.LoadingAnimation
import it.branjsmo.jetpackmvi.presentation.screen.create.viewModel.CreateAction
import it.branjsmo.jetpackmvi.presentation.screen.create.viewModel.CreatePostUiState
import it.branjsmo.jetpackmvi.presentation.theme.JetpackMviTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePostScreen(
    uiState: CreatePostUiState,
    onAction: (CreateAction) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.create_title)) },
                navigationIcon = {
                    IconButton(onClick = { onAction(CreateAction.OnBackClick) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.detail_back)
                        )
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = uiState.title,
                    onValueChange = { onAction(CreateAction.OnTitleChange(it)) },
                    label = { Text(stringResource(R.string.field_title)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = uiState.body,
                    onValueChange = { onAction(CreateAction.OnBodyChange(it)) },
                    label = { Text(stringResource(R.string.field_body)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )

                if (uiState.error != null) {
                    Text(
                        text = stringResource(uiState.error),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Button(
                    onClick = { onAction(CreateAction.OnCreateClick) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !uiState.isLoading
                ) {
                    if (uiState.isLoading) {
                        LoadingAnimation(circleSize = 8.dp, circleColor = MaterialTheme.colorScheme.onPrimary)
                    } else {
                        Text(stringResource(R.string.button_create))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CreatePostScreenPreview() {
    JetpackMviTheme {
        CreatePostScreen(
            uiState = CreatePostUiState(),
            onAction = {}
        )
    }
}
