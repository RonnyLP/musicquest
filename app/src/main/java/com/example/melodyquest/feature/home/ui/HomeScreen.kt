package com.example.melodyquest.feature.home.ui

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.melodyquest.core.ui.components.AppHeader
import com.example.melodyquest.feature.home.viewmodel.FakeHomeScreenViewModel
import com.example.melodyquest.feature.home.viewmodel.FakeInicioTabViewModel
import com.example.melodyquest.feature.home.viewmodel.HomeScreenViewModel
import com.example.melodyquest.feature.home.viewmodel.IHomeScreenViewModel
import com.example.melodyquest.feature.home.viewmodel.IInicioTabViewModel


@Preview
@Composable
fun HomeScreenPreview(
    fakeViewModel: IHomeScreenViewModel = FakeHomeScreenViewModel()
) {

    HomeScreen(
        viewModel = fakeViewModel,
        onNavigateToPlayer = {},
        preview = true
    )
}


@Composable
fun HomeScreen(
    viewModel: IHomeScreenViewModel = viewModel<HomeScreenViewModel>(),
    onNavigateToPlayer: () -> Unit,
    preview: Boolean = false
) {
    val selectedTab = viewModel.selectedTab.value

    Scaffold(
        topBar = {
            if (selectedTab != HomeTab.Perfil) {
                AppHeader(selectedTab.title)
            }
        },
        bottomBar = {
            NavigationBar {
                HomeTab.entries.forEach { tab ->
                    NavigationBarItem(
                        selected = selectedTab == tab,
                        onClick = { viewModel.onSelectedTab(tab) },
                        icon ={
                            Icon(
                                imageVector = tab.icon,
                                contentDescription = tab.title,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        label = { Text(tab.title) }
                    )
                }
            }
        }
    ) { innerPadding ->
        if (preview) {
            when (selectedTab) {
                HomeTab.Inicio -> InicioTabPreview()
                HomeTab.Biblioteca -> BibliotecaTab(innerPadding, onNavigateToPlayer = onNavigateToPlayer)
                HomeTab.Perfil -> UsuarioTab(innerPadding)
            }

        } else {
            when (selectedTab) {
                HomeTab.Inicio -> InicioTab(innerPadding, onNavigateToPlayer = onNavigateToPlayer)
                HomeTab.Biblioteca -> BibliotecaTab(innerPadding, onNavigateToPlayer = onNavigateToPlayer)
                HomeTab.Perfil -> UsuarioTab(innerPadding)
            }
        }
    }
}

