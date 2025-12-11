package com.example.melodyquest.feature.home.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.melodyquest.feature.home.ui.HomeTab

interface IHomeScreenViewModel {
    val selectedTab: State<HomeTab>
    fun onSelectedTab(tab: HomeTab) {}
}

class HomeScreenViewModel: ViewModel(), IHomeScreenViewModel {
    private val _selectedTab = mutableStateOf(HomeTab.Inicio)
    override val selectedTab: State<HomeTab> = _selectedTab

    override fun onSelectedTab(tab: HomeTab) {
        _selectedTab.value = tab
    }
}

class FakeHomeScreenViewModel: ViewModel(), IHomeScreenViewModel {
    override val selectedTab = mutableStateOf(HomeTab.Inicio)
}