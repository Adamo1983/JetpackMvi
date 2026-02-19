package it.branjsmo.jetpackmvi.presentation.screen.edit.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import it.branjsmo.jetpackmvi.R
import it.branjsmo.jetpackmvi.domain.model.PostTheme
import it.branjsmo.jetpackmvi.presentation.components.LoadingAnimation
import it.branjsmo.jetpackmvi.presentation.screen.edit.viewModel.EditAction
import it.branjsmo.jetpackmvi.presentation.screen.edit.viewModel.EditPostUiState
import it.branjsmo.jetpackmvi.presentation.theme.JetpackMviTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPostScreen(
    uiState: EditPostUiState,
    onAction: (EditAction) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.edit_title)) },
                navigationIcon = {
                    IconButton(onClick = { onAction(EditAction.OnBackClick) }) {
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
            if (uiState.isLoading && uiState.title.isEmpty()) {
                LoadingAnimation(modifier = Modifier.align(Alignment.Center))
            } else {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedTextField(
                        value = uiState.title,
                        onValueChange = { onAction(EditAction.OnTitleChange(it)) },
                        label = { Text(stringResource(R.string.field_title)) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = stringResource(uiState.theme.toResId()),
                            onValueChange = {},
                            readOnly = true,
                            label = { Text(stringResource(R.string.field_theme)) },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            modifier = Modifier
                                .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                                .fillMaxWidth()
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            PostTheme.entries.forEach { theme ->
                                DropdownMenuItem(
                                    text = { Text(stringResource(theme.toResId())) },
                                    onClick = {
                                        onAction(EditAction.OnThemeChange(theme))
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                    OutlinedTextField(
                        value = uiState.body,
                        onValueChange = { onAction(EditAction.OnBodyChange(it)) },
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
                        onClick = { onAction(EditAction.OnSaveClick) },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !uiState.isLoading
                    ) {
                        if (uiState.isLoading) {
                            LoadingAnimation(circleSize = 8.dp, circleColor = MaterialTheme.colorScheme.onPrimary)
                        } else {
                            Text(stringResource(R.string.button_save_changes))
                        }
                    }
                }
            }
        }
    }
}

private fun PostTheme.toResId(): Int {
    return when (this) {
        PostTheme.LANDSCAPE -> R.string.theme_landscape
        PostTheme.ROBOT -> R.string.theme_robot
        PostTheme.AVATAR -> R.string.theme_avatar
        PostTheme.TECH -> R.string.theme_tech
        PostTheme.KITTEN -> R.string.theme_kitten
        PostTheme.FOOD -> R.string.theme_food
        PostTheme.NATURE -> R.string.theme_nature
        PostTheme.BEARD -> R.string.theme_beard
        PostTheme.CAGE -> R.string.theme_cage
    }
}

@Preview(showBackground = true)
@Composable
private fun EditPostScreenPreview() {
    JetpackMviTheme {
        EditPostScreen(
            uiState = EditPostUiState(
                title = "Titolo",
                body = "Corpo del post",
                theme = PostTheme.TECH
            ),
            onAction = {}
        )
    }
}
