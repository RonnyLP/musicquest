@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.melodyquest.feature.home.ui

import android.util.Log
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.melodyquest.core.theme.Green500
import com.example.melodyquest.core.ui.components.GrayButton
import com.example.melodyquest.core.ui.components.GreenButton
import com.example.melodyquest.core.ui.components.RedButton
import com.example.melodyquest.core.ui.icons.AppIcons
import com.example.melodyquest.core.ui.icons.Search
import com.example.melodyquest.core.ui.icons.ThreeDots
import com.example.melodyquest.feature.home.viewmodel.InicioEvent
import com.example.melodyquest.feature.home.viewmodel.InicioTabViewModel


@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
fun InicioTabPreview() {
    InicioTab(
        innerPadding = PaddingValues(0.dp)
    )
}


@Composable
fun InicioTab(
    innerPadding: PaddingValues,
    inicioTabViewModel: InicioTabViewModel = hiltViewModel(),
    onNavigateToPlayer: () -> Unit = {}
) {

    LaunchedEffect(Unit) {
        inicioTabViewModel.events.collect { event ->
            when (event) {
                is InicioEvent.NavigateToPlayer -> onNavigateToPlayer()
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

        SearchWrapper()

        GreenButton("Agregar", { inicioTabViewModel.openAddDialog() })

        Spacer(Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(inicioTabViewModel.progresionesGuardadas) { prog ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(16.dp, 0.dp)
                        .clickable {
                            inicioTabViewModel.navigateToPlayer()
                        }
                ) {
                    Text(
                        prog,
                        modifier =  Modifier
                            .weight(1f)
                    )
                    Box(
                        modifier = Modifier
//                            .size(32.dp)
                            .clickable {
                                inicioTabViewModel.openEditDialog()
                            }
                    ) {
                        Icon(
                            imageVector = AppIcons.ThreeDots,
                            contentDescription = "Editar",
                            tint = Color.DarkGray,
                            modifier = Modifier
                                .size(32.dp)
                                .align(Alignment.Center)
                                .rotate(90f)
                        )
                    }
                }

            }
        }

        AgregarDialog(
            inicioTabViewModel.isAddDialogOpen.value,
            onDismiss = { inicioTabViewModel.closeAddDialog() },
            onSubmit = { inicioTabViewModel.closeAddDialog() }
        )


        EditarDialog(
            inicioTabViewModel.isEditDialogOpen.value,
            onDismiss = { inicioTabViewModel.closeEditDialog() },
            onEdit = { inicioTabViewModel.closeEditDialog() },
            onDelete = { inicioTabViewModel.closeEditDialog() }
        )
    }
}








@Composable
fun SearchWrapper() {

    var query by remember { mutableStateOf(TextFieldValue("")) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(16.dp, 0.dp)
            .border(2.dp, Color.Black)

    ) {


        BasicTextField(
            value = query,
            onValueChange = {query = it},
            singleLine = true,
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .padding(8.dp, 16.dp)

                ) {
                    innerTextField()
                }
            },
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()

        )
        Box(
            modifier = Modifier
                .padding(8.dp)
                .size(32.dp)
        ) {
            Icon(
                imageVector = AppIcons.Search,
                contentDescription = "Search",
                tint = Color.DarkGray,
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.Center)
            )
        }
    }
}


@Composable
fun AgregarDialog(
    agregarDialog: Boolean,
    onDismiss: () -> Unit,
    onSubmit: () -> Unit
) {
    if (agregarDialog) {
        Dialog(
            onDismissRequest = { onDismiss() },

            ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .background(Color.White)
                    .widthIn(max = 320.dp)
                    .fillMaxWidth()
                    .padding(32.dp, 24.dp)
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

                var isSelectExpanded by remember { mutableStateOf(false) }
                var plantillas = listOf("Opción 1", "Opción 2", "Opción 3")
                var seleccionado by remember { mutableStateOf(plantillas[0]) }

                ExposedDropdownMenuBox(
                    expanded = isSelectExpanded,
                    onExpandedChange = { isSelectExpanded = !isSelectExpanded }
                ) {
                    BasicTextField(
                        value = seleccionado,
                        onValueChange = {  },
                        readOnly = true,
                        modifier = Modifier
                            .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                            .fillMaxWidth()
                            .border(
                                2.dp,
                                color = Color.Black,
                                shape = RoundedCornerShape(8.dp)
                            ),
                        decorationBox = { innerTextField ->
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxWidth()
                            ) {
                                innerTextField()
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isSelectExpanded)

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
                    horizontalArrangement = Arrangement.spacedBy(32.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    GrayButton(
                        "Volver",
                        { onDismiss() },
                        modifier = Modifier
                            .weight(1f)
                    )
                    GreenButton(
                        "Agregar",
                        { onSubmit() },
                        modifier = Modifier
                            .weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun EditarDialog(
    editarDialog: Boolean,
    onDismiss: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    if (editarDialog) {
        Dialog(
            onDismissRequest = { onDismiss() }
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .background(Color.White)
                    .widthIn(max = 320.dp)
                    .fillMaxWidth()
                    .padding(32.dp, 24.dp)
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


                GreenButton(
                    "Cambiar nombre",
                    onClick={ onEdit() },
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(32.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    GrayButton(
                        "Volver",
                        { onDismiss() },
                        modifier = Modifier
                            .weight(1f)
                    )
                    RedButton(
                        "Eliminar",
                        { onDelete() },
                        modifier = Modifier
                            .weight(1f)
                    )
                }
            }
        }
    }

}