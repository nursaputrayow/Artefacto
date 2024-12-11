package com.nursaputrayow.artefacto.screen

import android.media.MediaPlayer
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nursaputrayow.artefacto.R
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.ui.zIndex
import com.nursaputrayow.artefacto.model.LoginRequest
import com.nursaputrayow.artefacto.network.ApiClient
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onRegisterScreen: () -> Unit,
    onHomeScreen: () -> Unit,
) {
    var mediaPlayer: MediaPlayer? by remember { mutableStateOf(null) }
    var isMusicPlaying by remember { mutableStateOf(true) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    val images = listOf(
        R.drawable.onboarding_image,
    )

    val locations = listOf(
        "Prambanan Temple, \n" + "Indonesia",
    )

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        setMaxVolume(context)

        mediaPlayer = MediaPlayer.create(context, R.raw.background_music)
        mediaPlayer?.apply {
            isLooping = true
            setVolume(1.0f, 1.0f)
            if (isMusicPlaying) start()
        }
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> {
                    if (mediaPlayer?.isPlaying == true) {
                        mediaPlayer?.pause()
                    }
                }
                Lifecycle.Event.ON_RESUME -> {
                    if (isMusicPlaying && mediaPlayer != null) {
                        mediaPlayer?.start()
                    } else if (isMusicPlaying) {
                        mediaPlayer = MediaPlayer.create(context, R.raw.background_music).apply {
                            isLooping = true
                            setVolume(1.0f, 1.0f)
                            start()
                        }
                    }
                }
                Lifecycle.Event.ON_STOP, Lifecycle.Event.ON_DESTROY -> {
                    mediaPlayer?.stop()
                    mediaPlayer?.release()
                    mediaPlayer = null
                }
                else -> {}
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }

    fun toggleMusic() {
        if (isMusicPlaying) {
            mediaPlayer?.pause()
        } else {
            mediaPlayer?.start()
        }
        isMusicPlaying = !isMusicPlaying
    }

    suspend fun loginUser() {
        isLoading = true
        val api = ApiClient.authApi
        try {
            val request = LoginRequest(
                phone = "+6282112297031",
                password = password,
            )
            val response = api.login(request)
            if (response.isSuccessful && response.body()?.status == "success") {
                Toast.makeText(context, response.body()?.message, Toast.LENGTH_LONG).show()
                coroutineScope.launch {
                    kotlinx.coroutines.delay(3000)
                    onHomeScreen()
                }
            } else {
                Toast.makeText(context, "Login failed: ${response.body()?.message}", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            Log.e("LoginError", "Error: ${e.message}")
        } finally {
            isLoading = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = images[0]),
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)) // Latar belakang redup
                    .zIndex(1f),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(80.dp),
                    color = Color(0xFFD6A266),
                    strokeWidth = 10.dp,
                    strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxSize()
                .padding(28.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.Start
        ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(15.dp),
                    elevation = CardDefaults.cardElevation(0.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier.padding(28.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.authentication_image),
                            contentDescription = "Authentication Image",
                            modifier = Modifier
                                .size(150.dp).align(Alignment.CenterHorizontally),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.height(40.dp))

                        Text("Email")

                        Spacer(modifier = Modifier.height(8.dp))

                        TextField(
                            value = email,
                            onValueChange = { email = it },
                            placeholder = { Text("Enter your email") },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Next
                            ),
                            colors = TextFieldDefaults.textFieldColors(
                                disabledTextColor = Color.Gray,
                                containerColor = Color.Transparent,
                                unfocusedPlaceholderColor = Color(0xFF6E6E6E),
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(2.dp, Color(0xFFC7C7C7), RoundedCornerShape(10.dp))
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Text("Password")

                        Spacer(modifier = Modifier.height(8.dp))

                        TextField(
                            value = password,
                            onValueChange = { password = it },
                            placeholder = { Text("Enter your password") },
                            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Done
                            ),
                            trailingIcon = {
                                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                                    Icon(
                                        painter = painterResource(
                                            id = if (passwordVisibility) R.drawable.icon_eye_open else R.drawable.icon_eye_closed
                                        ),
                                        contentDescription = "Toggle Password Visibility",
                                        tint = Color.Gray,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                disabledTextColor = Color.Gray,
                                containerColor = Color.Transparent,
                                unfocusedPlaceholderColor = Color(0xFF6E6E6E),
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(2.dp, Color(0xFFC7C7C7), RoundedCornerShape(10.dp))
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Row {
                            Text("Don't have an account?")
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Register",
                                modifier = Modifier.clickable(onClick = { onRegisterScreen() }).padding(top = 3.dp),
                                style = TextStyle(
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 70.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_location),
                            contentDescription = "Location Icon",
                            tint = Color(0xFFD6A266),
                            modifier = Modifier.size(15.dp),
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = locations[0],
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color.White
                            )
                        )
                    }
                    Button(
                        onClick = {
                            if (email.isNotEmpty() && password.isNotEmpty()) {
                                coroutineScope.launch {
                                    loginUser()
                                }
                            } else {
                                Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier
                            .size(120.dp, 50.dp),
                        shape = RoundedCornerShape(15.dp),
                        border = BorderStroke(2.dp, Color(0xFFD6A266)),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color(0xFFD6A266),
                            contentColor = Color(0xFFD6A266),
                        ),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Login",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.Black
                                )
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Icon(
                                painter = painterResource(id = R.drawable.icon_next),
                                contentDescription = "Next Icon",
                                tint = Color.Black,
                                modifier = Modifier.size(15.dp)
                            )
                        }
                    }
                }
            }

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 28.dp, top = 70.dp)
        ) {
            OutlinedButton(
                onClick = { toggleMusic() },
                modifier = Modifier.size(50.dp),
                shape = RoundedCornerShape(15.dp),
                border = BorderStroke(2.dp, Color(0xFFD6A266)),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Transparent,
                ),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(
                    painter = painterResource(
                        id = if (isMusicPlaying) R.drawable.icon_sound else R.drawable.icon_insound
                    ),
                    contentDescription = "Sound Icon",
                    tint = Color(0xFFD6A266),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}