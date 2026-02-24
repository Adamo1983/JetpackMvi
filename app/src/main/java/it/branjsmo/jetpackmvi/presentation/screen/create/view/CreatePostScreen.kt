package it.branjsmo.jetpackmvi.presentation.screen.create.view

import android.Manifest
import android.net.Uri
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import coil.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import it.branjsmo.jetpackmvi.R
import it.branjsmo.jetpackmvi.presentation.components.LoadingAnimation
import it.branjsmo.jetpackmvi.presentation.screen.create.viewModel.CreateAction
import it.branjsmo.jetpackmvi.presentation.screen.create.viewModel.CreatePostUiState
import it.branjsmo.jetpackmvi.presentation.theme.JetpackMviTheme
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun CreatePostScreen(
    uiState: CreatePostUiState,
    onAction: (CreateAction) -> Unit
) {
    val context = LocalContext.current
    var tempImageUri by remember { mutableStateOf<Uri?>(null) }

    // Launcher per la Galleria
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            onAction(CreateAction.OnImageSelected(uri))
        }
    }

    // Launcher per la Fotocamera
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            onAction(CreateAction.OnImageSelected(tempImageUri))
        }
    }

    // Gestione permessi fotocamera
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    fun launchCamera() {
        val file = File.createTempFile(
            "JPEG_${SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())}_",
            ".jpg",
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        )
        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
        tempImageUri = uri
        cameraLauncher.launch(uri)
    }

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
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Sezione Immagine
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .clickable {
                            // Opzionale: apri dialog scelta
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (uiState.selectedImageUri != null) {
                        AsyncImage(
                            model = uiState.selectedImageUri,
                            contentDescription = "Selected Image",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.BrokenImage,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                "Nessuna immagine selezionata",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {
                            if (cameraPermissionState.status.isGranted) {
                                launchCamera()
                            } else {
                                cameraPermissionState.launchPermissionRequest()
                            }
                        },
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(horizontal = 8.dp)
                    ) {
                        Icon(Icons.Default.AddAPhoto, contentDescription = null)
                        Spacer(Modifier.width(4.dp))
                        Text("Camera", style = MaterialTheme.typography.labelSmall)
                    }

                    Button(
                        onClick = {
                            galleryLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        },
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(horizontal = 8.dp)
                    ) {
                        Icon(Icons.Default.PhotoLibrary, contentDescription = null)
                        Spacer(Modifier.width(4.dp))
                        Text("Galleria", style = MaterialTheme.typography.labelSmall)
                    }

                    OutlinedButton(
                        onClick = { onAction(CreateAction.OnImageSelected(null)) },
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(horizontal = 8.dp)
                    ) {
                        Text("Default", style = MaterialTheme.typography.labelSmall)
                    }
                }

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
                        .heightIn(min = 150.dp)
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
            uiState = CreatePostUiState(
                title = "Nuovo Post",
                body = "Descrizione del contenuto del post..."
            ),
            onAction = {}
        )
    }
}
