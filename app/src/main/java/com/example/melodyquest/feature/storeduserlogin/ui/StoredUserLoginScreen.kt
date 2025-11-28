package com.example.melodyquest.feature.storeduserlogin.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.melodyquest.core.ui.components.GreenButton
import com.example.melodyquest.core.ui.icons.AppIcons
import com.example.melodyquest.core.ui.icons.User


@Composable
fun StoredUserLoginScreen(username: String) {
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(Color.White)
                    .align(Alignment.BottomStart)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, top = 32.dp, bottom = 16.dp, end = 0.dp)
            ) {
                Text(
                    "Iniciar sesión",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineMedium,
                )
                Spacer(Modifier.height(80.dp))
                Box(
                    modifier = Modifier
                        .size(160.dp)
                        .align(Alignment.CenterHorizontally)
                        .background(Color.White, shape = CircleShape)
                        .clip(CircleShape)
                        .border(2.dp, Color.Black, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = AppIcons.User,
                        contentDescription = "User Icon",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(100.dp)
                    )
                }

            }


        }



        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = username, style = MaterialTheme.typography.headlineSmall, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(32.dp))
            OutlinedTextField(value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth())
        }

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .weight(1f)
                .padding(16.dp, 0.dp)
        ) {
            GreenButton(
                text = "Iniciar sesión",
                onClick = { /* Lógica de inicio de sesión */ },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StoredUserLoginScreenPreview() {
    StoredUserLoginScreen(username = "UsuarioEjemplo")
}