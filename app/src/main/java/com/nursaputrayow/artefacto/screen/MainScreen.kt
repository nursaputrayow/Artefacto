package com.nursaputrayow.artefacto.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment

@Composable
fun MainScreen() {
    var selectedScreen by remember { mutableStateOf("home") }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedScreen = selectedScreen,
                onScreenSelected = { screen -> selectedScreen = screen }
            )
        },
        containerColor = Color(0xFFFFFFFF)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFD6A266), shape = RoundedCornerShape(12.dp)),
        ) {
            when (selectedScreen) {
                "home" -> HomeScreen()
                "settings" -> SettingScreen()
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    selectedScreen: String,
    onScreenSelected: (String) -> Unit
) {
    NavigationBar(
        modifier = Modifier.height(120.dp), // Atur tinggi BottomNavigationBar
        containerColor = Color(0xFFF8F8F8),
        tonalElevation = 0.dp,
//        contentColor = Color.Red
    ) {
        NavigationBarItem(
            selected = selectedScreen == "home",
            onClick = { onScreenSelected("home") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Transparent, // Customize the color for selected state
                unselectedIconColor = Color.Transparent, // Customize the color for unselected state
                selectedTextColor = Color.Transparent,  // Customize the color for selected text
                unselectedTextColor = Color.Transparent,  // Customize the color for unselected text
                indicatorColor = Color.Transparent

            ),
            icon = {
                BottomNavigationIcon(
                    isSelected = selectedScreen == "home",
                    icon = Icons.Default.Home,
                    label = "Home"
                )
            },
        )
        NavigationBarItem(
            selected = selectedScreen == "settings",
            onClick = { onScreenSelected("settings") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Transparent, // Customize the color for selected state
                unselectedIconColor = Color.Transparent, // Customize the color for unselected state
                selectedTextColor = Color.Transparent,  // Customize the color for selected text
                unselectedTextColor = Color.Transparent,  // Customize the color for unselected text
                indicatorColor = Color.Transparent

            ),
            icon = {
                BottomNavigationIcon(
                    isSelected = selectedScreen == "settings",
                    icon = Icons.Default.Settings,
                    label = "Settings"
                )
            },
        )
    }
}

@Composable
fun BottomNavigationIcon(
    isSelected: Boolean,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String
) {
    Box(
        modifier = Modifier
            .size(60.dp)
            .background(

                if (isSelected)
                Color(0xFFD6A266)
                else Color.Transparent
        , shape = RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (isSelected) Color(0xFFFFFFFF) else Color(0xFFD6D6D6),
            modifier = Modifier
                .size(30.dp)
        )
    }
}