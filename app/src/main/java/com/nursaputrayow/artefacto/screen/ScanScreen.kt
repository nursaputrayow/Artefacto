package com.nursaputrayow.artefacto.screen

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun ScanScreen(
    onNavigateBack: () -> Unit
) {
    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }

    // Use context safely from @Composable
    val context = LocalContext.current

    // Launcher for picking an image from gallery
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                val inputStream = context.contentResolver.openInputStream(uri)
                imageBitmap = BitmapFactory.decodeStream(inputStream)
            }
        }
    )

    // Launcher for taking a photo using the camera
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = { bitmap ->
            imageBitmap = bitmap
        }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Scan Screen",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Display the scanned or selected image
            imageBitmap?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = "Scanned Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            Button(
                onClick = { cameraLauncher.launch(null) }, // Pass null explicitly as input
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Scan from Camera")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { galleryLauncher.launch("image/*") }, // Correct MIME type for gallery
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Select from Gallery")
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onNavigateBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Back to Home")
            }
        }
    }
}
