package com.example.testapp.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.testapp.components.DoorsCard
import com.example.testapp.models.Door

@Composable
fun DoorsPageScreen(doorsList:List<Door>, renameAction: (item: Door)->Unit ) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(doorsList) {
            DoorsCard(it, renameAction)
        }
    }
}