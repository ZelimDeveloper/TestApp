package com.example.testapp.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.testapp.components.CameraCard
import com.example.testapp.models.Camera

@Composable
fun CameraPageScreen(camerasList:List<Camera>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(camerasList) {
            CameraCard(it)
        }
    }
}