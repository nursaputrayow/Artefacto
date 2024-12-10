package com.nursaputrayow.artefacto.screen

import android.media.MediaPlayer
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nursaputrayow.artefacto.R
import android.content.Context
import android.media.AudioManager
import androidx.compose.foundation.clickable
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver

fun setMaxVolume(context: Context) {
    val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0)
}

@Composable
fun OnboardingScreen(
    onLoginScreen: () -> Unit,
) {
    var currentPage by remember { mutableIntStateOf(0) }
    var mediaPlayer: MediaPlayer? by remember { mutableStateOf(null) }
    var isMusicPlaying by remember { mutableStateOf(true) }

    val onboardingTexts = listOf(
        "Discover the \n" +"Stories of the \n" +"Past!",
        "Scan Artifacts \n" +"and Historical \n" +"Treasures!",
        "Book Your \n" +"Ticket to \n" +"Temples!"
    )

    val images = listOf(
        R.drawable.onboarding_image,
        R.drawable.onboarding_image,
        R.drawable.onboarding_image
    )

    val locations = listOf(
        "Prambanan Temple, \n" +"Indonesia",
        "Prambanan Temple, \n" +"Indonesia",
        "Prambanan Temple, \n" +"Indonesia",
    )

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

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

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = images[currentPage]),
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
            .padding(28.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = onboardingTexts[currentPage],
                style = TextStyle(
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.align(Alignment.Start),
                horizontalArrangement = Arrangement.Center
            ) {
                onboardingTexts.forEachIndexed { index, _ ->
                    val indicatorWidth = if (index == currentPage) 50.dp else 30.dp
                    val indicatorHeight = 5.dp

                    Box(
                        modifier = Modifier
                            .width(indicatorWidth)
                            .height(indicatorHeight)
                            .padding(horizontal = 5.dp)
                            .clip(RoundedCornerShape(50))
                            .background(
                                if (index == currentPage) Color(0xFFD6A266) else Color.White
                            )
                            .clickable {
                                currentPage = index
                            }
                    )
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
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = locations[currentPage],
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.White
                        )
                    )
                }
                Button(
                    onClick = {
                        if (currentPage < 2) {
                            currentPage++
                        } else {
                            onLoginScreen()
                        }
                    },
                    modifier = Modifier
                        .size(if (currentPage > 1) 120.dp else 50.dp, 50.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color(0xFFD6A266),
                        contentColor = Color(0xFFD6A266),
                    ),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    if (currentPage > 1) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Explore",
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
                    } else {
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