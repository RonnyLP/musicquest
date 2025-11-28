package com.example.melodyquest.feature.home.ui

//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.AccountCircle
//import androidx.compose.material.icons.filled.Home
//import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.painter.Painter
import com.example.melodyquest.R
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import com.example.melodyquest.core.ui.icons.AppIcons
import com.example.melodyquest.core.ui.icons.Home
import com.example.melodyquest.core.ui.icons.Library
import com.example.melodyquest.core.ui.icons.User

//sealed class HomeTab(
//    val route: String,
//    val title: String,
//    val icon: ImageVector
//) {
//    object Inicio : HomeTab("inicio", "Inicio", Icons.Filled.Home)
//    object Biblioteca : HomeTab("biblioteca", "Biblioteca", Icons.Filled.Menu)
//    object Perfil : HomeTab("perfil", "Perfil", Icons.Filled.AccountCircle)
//
//    companion object {
//        val allTabs = listOf(Inicio, Biblioteca, Perfil)
//    }
//}
enum class HomeTab(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    Inicio("inicio", "Inicio", AppIcons.Home),
    Biblioteca("biblioteca", "Biblioteca", AppIcons.Library),
    Perfil("perfil", "Perfil", AppIcons.User);
}