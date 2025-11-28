package com.example.melodyquest.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.melodyquest.core.theme.Gray500
import com.example.melodyquest.core.theme.Green500
import com.example.melodyquest.core.theme.Red500


@Preview
@Composable
fun ButtonsPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Color.White)
            .padding(32.dp)
    ) {
        GreenButton("Verde", {})
        RedButton("Rojo", {})
        GrayButton("Gris", {})
        TransparentButton("Transparente", {})
    }
}



@Composable
fun GreenButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Green500,
            contentColor = Color.White
        ),
        modifier = modifier
    ) {
        Text(text)
    }
}


@Composable
fun GrayButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Gray500,
        ),
        modifier = modifier
    ) {
        Text(text)
    }
}

@Composable
fun RedButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Red500,
        ),
        modifier = modifier
    ) {
        Text(text)
    }
}

@Composable
fun TransparentButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.DarkGray
        ),
        modifier = modifier
    ) {
        Text(text)
    }
}