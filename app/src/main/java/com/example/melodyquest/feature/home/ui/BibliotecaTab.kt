@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.melodyquest.feature.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.melodyquest.core.theme.Green500
import com.example.melodyquest.core.ui.components.GrayButton
import com.example.melodyquest.core.ui.components.GreenButton
import com.example.melodyquest.feature.home.viewmodel.BibliotecaEvent
import com.example.melodyquest.feature.home.viewmodel.BibliotecaTabViewModel


@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
fun BibliotecaTabPreview() {
    BibliotecaTab(
    )
}

@Composable
fun BibliotecaTab(
    innerPadding:  PaddingValues = PaddingValues(0.dp),
    bibliotecaTabViewModel: BibliotecaTabViewModel = viewModel(),
    onNavigateToPlayer: () -> Unit = {}
) {

    LaunchedEffect(Unit) {
        bibliotecaTabViewModel.events.collect { event ->
            when (event) {
                BibliotecaEvent.NavigateToPlayer -> {
                    onNavigateToPlayer()
                }
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxWidth()
    ) {
        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .padding(16.dp, 0.dp)
        ) {
            Text("Canciones populares", fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
        }


        Spacer(Modifier.height(16.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            bibliotecaTabViewModel.popularSongs.forEach { prog ->
                Row(
                    modifier = Modifier
                        .padding(16.dp, 0.dp)
                ) {
                    Text(
                        prog,
                        modifier =  Modifier
                            .weight(1f)
                            .clickable {
                                bibliotecaTabViewModel.navigateToPlayer()
                            }
                    )
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(Green500)

                    )
                }

            }
        }


        Row(
            modifier = Modifier
                .padding(16.dp, 0.dp)
        ) {
            Text("Progresiones comunes", fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
        }

        Spacer(Modifier.height(16.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            bibliotecaTabViewModel.commonProgressions.forEach { prog ->
                Row(
                    modifier = Modifier
                        .padding(16.dp, 0.dp)
                ) {
                    Text(
                        prog,
                        modifier =  Modifier
                            .weight(1f)
                            .clickable {
                                bibliotecaTabViewModel.navigateToPlayer()
                            }
                    )
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(Green500)
                    )
                }

            }
        }

        ClonarDialog(
            bibliotecaTabViewModel.isCloneDialogOpen.value,
            onDismiss = { bibliotecaTabViewModel.openCloneDialog() },
            onSubmit = { bibliotecaTabViewModel.closeCloneDialog() }
        )



    }

}

@Composable
fun ClonarDialog(
    clonarDialog: Boolean,
    onDismiss: () -> Unit,
    onSubmit: () -> Unit
) {
    if (clonarDialog) {
        Dialog(
            onDismissRequest = { onDismiss() },

            ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .background(Color.White)
                    .widthIn(max = 320.dp)
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Nombre")
                BasicTextField(
                    value = "Californication",
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            2.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(8.dp)
                        ),
                    decorationBox = { innerTextField ->
                        Box(
                            Modifier
                                .padding(8.dp)
                        ) {
                            innerTextField()
                        }
                    }
                )

                Spacer(
                    Modifier
                        .height(8.dp)
                )

                Text("Seleccionar plantilla")

                var isSelectExpanded = false;
                var plantillas = listOf("Opción 1", "Opción 2", "Opción 3");
                var seleccionado by remember { mutableStateOf(plantillas[0]) }

                ExposedDropdownMenuBox(
                    expanded = isSelectExpanded,
                    onExpandedChange = { isSelectExpanded = !isSelectExpanded }
                ) {
                    BasicTextField(
                        value = seleccionado,
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                2.dp,
                                color = Color.Black,
                                shape = RoundedCornerShape(8.dp)
                            ),
                        decorationBox = { innerTextField ->
                            Box(
                                Modifier
                                    .padding(8.dp)
                            ) {
                                innerTextField()
                            }
                        }
                    )

                    ExposedDropdownMenu(
                        expanded = isSelectExpanded,
                        onDismissRequest = { isSelectExpanded = false }
                    ) {
                        plantillas.forEach { opcion ->
                            DropdownMenuItem(
                                text = { Text(opcion) },
                                onClick = {
                                    seleccionado = opcion
                                    isSelectExpanded = false
                                }
                            )
                        }
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    GrayButton("Volver", { onDismiss() })
                    GreenButton("Agregar", { onDismiss() })
                }
            }
        }
    }
}