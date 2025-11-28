package com.example.melodyquest.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun AppHeaderPreview() {
    Column(
        Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        AppHeader("Título", "Enlace", {})
    }
}


@Composable
fun AppHeader(title: String, linkText: String = "", navigate: () -> Unit = {}) {

    Column (
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .background(Color.Black)
            .fillMaxWidth()
            .padding(16.dp, 32.dp)
    ) {
        Text(
            linkText,
            color = Color.White,
            modifier = Modifier
                .clickable() {
                    navigate()
                }
        )

        Text(title, color = Color.White, fontSize = 32.sp)
    }
}

