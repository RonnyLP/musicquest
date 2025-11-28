package com.example.melodyquest.feature.home.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.melodyquest.feature.home.ui.HomeTab

class HomeScreenViewModel: ViewModel() {
    private val _selectedTab = mutableStateOf(HomeTab.Inicio)
    val selectedTab: State<HomeTab> = _selectedTab

    fun onSelectedTab(tab: HomeTab) {
        _selectedTab.value = tab
    }
}