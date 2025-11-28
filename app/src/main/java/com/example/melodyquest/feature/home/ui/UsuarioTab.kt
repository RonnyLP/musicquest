package com.example.melodyquest.feature.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.melodyquest.core.ui.components.GreenButton
import com.example.melodyquest.core.ui.icons.AppIcons
import com.example.melodyquest.core.ui.icons.User
import com.example.melodyquest.feature.home.viewmodel.UsuarioTabViewModel


@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
fun UsuarioTabPreview() {
    UsuarioTab()
}


@Composable
fun UsuarioTab(
    innerPadding: PaddingValues = PaddingValues(0.dp),
    usuarioTabViewModel: UsuarioTabViewModel = viewModel()
) {
    Column (
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .padding(16.dp, 32.dp)
    ) {
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(16.dp, 0.dp)
        ) {
            Image(
                AppIcons.User,
                contentDescription = "Usuario",
                modifier = Modifier
                    .size(160.dp)
            )
            Text(
                text  = "Inicia sesión para guardar tus pistas y sincronizarlas en varios dispositivos",
                textAlign = TextAlign.Center

            )
        }
        GreenButton(
            "Iniciar sesión con Google",
            { usuarioTabViewModel.startGoogleAuth() },
            modifier = Modifier
                .fillMaxWidth()
        )
    }


}