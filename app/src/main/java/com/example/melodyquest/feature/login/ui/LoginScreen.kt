package com.example.melodyquest.feature.login.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.melodyquest.core.ui.components.GreenButton
import com.example.melodyquest.core.ui.components.TransparentButton
import com.example.melodyquest.core.ui.icons.AppIcons
import com.example.melodyquest.core.ui.icons.User
import com.example.melodyquest.feature.login.viewmodel.AuthViewModel
import com.example.melodyquest.feature.login.viewmodel.FAuthViewModel
import com.example.melodyquest.feature.login.viewmodel.FSelectAccountViewModel
import com.example.melodyquest.feature.login.viewmodel.IAuthViewModel
import com.example.melodyquest.feature.login.viewmodel.ISelectAccountViewModel
import com.example.melodyquest.feature.login.viewmodel.LoginViewModel
import com.example.melodyquest.feature.login.viewmodel.PreviewAuthViewModel
import com.example.melodyquest.feature.login.viewmodel.SelectAccountViewModel


@Composable
fun WelcomeScreen(
    onNavigateToSelectAccount: () -> Unit,
    viewModel: LoginViewModel = viewModel()
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(32.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.Center,
        ) {
            Text("MusicQuest", fontSize = 32.sp, fontWeight = FontWeight.Bold)
        }

        GreenButton(
            onClick = {
                viewModel.loginWithGoogle(onSuccess = onNavigateToSelectAccount)
            },
            text = "Iniciar sesión",
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}


@Preview
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen(onNavigateToSelectAccount = {})
}


@Preview
@Composable
fun SelectAccountScreenPreview(
    vm: ISelectAccountViewModel = FSelectAccountViewModel()
) {
    SelectAccountScreen(
        viewModel = vm,
        onNavigateToFullLogin = {},
        onNavigateToPasswordLogin = {}
    )
}



@Composable
fun SelectAccountScreen(
//    viewModel: ISelectAccountViewModel = viewModel(),
    viewModel: ISelectAccountViewModel = hiltViewModel<SelectAccountViewModel>(),
    onNavigateToPasswordLogin: (String) -> Unit,
    onNavigateToFullLogin: () -> Unit
) {
    val accounts by viewModel.savedAccounts.collectAsState()
//    val accounts = remember { listOf("Cuenta 1", "Cuenta 2", "Cuenta 3") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Seleccionar cuenta",
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Lista de cuentas guardadas
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(accounts) { account ->
                AccountItem(
                    username = account,
                    onClick = { onNavigateToPasswordLogin(account) }
                )
            }

            // Opción para añadir cuenta
            item {
                AccountItem(
                    username = "Añadir cuenta",
                    isAddAccount = true,
                    onClick = onNavigateToFullLogin
                )
            }
        }
    }
}

@Composable
fun AccountItem(
    username: String,
    isAddAccount: Boolean = false,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp),
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(
                        if (isAddAccount) Color(0xFF4CAF50)
                        else MaterialTheme.colorScheme.primary
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (isAddAccount) "+" else username.first().uppercase(),
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = username,
                fontSize = 16.sp,
                fontWeight = if (isAddAccount) FontWeight.Normal else FontWeight.Medium
            )
        }
    }
}




// ========== PANTALLA 3: Login con solo contraseña ==========

@Preview
@Composable
fun PasswordLoginScreenPreview(fakeVM: IAuthViewModel = FAuthViewModel()) {
    PasswordLoginScreen(
        "@username",
        viewModel = fakeVM,
        onNavigateBack = {},
        onLoginSuccess = {},
        onForgotPassword = {}
    )
}


@Composable
fun PasswordLoginScreen(
    username: String,
    viewModel: IAuthViewModel = hiltViewModel<AuthViewModel>(),
    onNavigateBack: () -> Unit,
    onLoginSuccess: () -> Unit,
    onForgotPassword: () -> Unit
) {
    var password by remember { mutableStateOf("") }
    val isLoading by viewModel.isLoading.collectAsState()
//    val isLoading by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        // Header con nombre de usuario
        Text(
            text = "Iniciar sesión",
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Avatar y nombre
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(Color.DarkGray),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = AppIcons.User,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(48.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = username,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Campo de contraseña
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Olvidé mi contraseña
        Text(
            text = "Olvidé mi contraseña",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .align(Alignment.End)
                .clickable { onForgotPassword() }
                .padding(8.dp),
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botón de iniciar sesión
        Button(
            onClick = {
                viewModel.loginWithPassword(username, password) { success ->
                    if (success) onLoginSuccess()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4CAF50)
            ),
            enabled = !isLoading && password.isNotEmpty()
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Color.White
                )
            } else {
                Text("Iniciar sesión", fontSize = 16.sp)
            }
        }
    }
}



// ========== PANTALLA 4: Login completo ==========
@Preview
@Composable
fun FullLoginScreenPreview(fakeVM: IAuthViewModel = FAuthViewModel()) {
    FullLoginScreen(
        fakeVM,
        onNavigateToRegister = {},
        onLoginSuccess = {},
        onForgotPassword = {}
    )
}


@Composable
fun FullLoginScreen(
    viewModel: IAuthViewModel = hiltViewModel<AuthViewModel>(),
    onNavigateToRegister: () -> Unit,
    onLoginSuccess: () -> Unit,
    onForgotPassword: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        Text(
            text = "Iniciar sesión",
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(48.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Usuario") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = errorMessage != null
        )

        if (errorMessage != null) {
            Text(
                text = errorMessage!!,
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Olvidé mi contraseña",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .align(Alignment.End)
                .clickable { onForgotPassword() }
                .padding(8.dp),
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                viewModel.login(username, password) { success ->
                    if (success) onLoginSuccess()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4CAF50)
            ),
            enabled = !isLoading && username.isNotEmpty() && password.isNotEmpty()
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Color.White
                )
            } else {
                Text("Iniciar sesión", fontSize = 16.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "¿No tienes una cuenta? ",
                fontSize = 14.sp
            )
            Text(
                text = "Regístrate",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.clickable { onNavigateToRegister() }
            )
        }
    }
}



// ========== PANTALLA 5: Registro ==========
@Preview
@Composable
fun RegisterScreenPreview(fakeVM: IAuthViewModel = FAuthViewModel()) {
    RegisterScreen(fakeVM, onNavigateToLogin = {}, onRegisterSuccess = {})
}


@Composable
fun RegisterScreen(
    viewModel: IAuthViewModel = hiltViewModel<AuthViewModel>(),
    onNavigateToLogin: () -> Unit,
    onRegisterSuccess: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val isLoading by viewModel.isLoading.collectAsState()
//    val isLoading by remember { mutableStateOf(false) }

    val passwordsMatch = password == confirmPassword
    val canRegister = username.isNotEmpty() &&
            password.isNotEmpty() &&
            confirmPassword.isNotEmpty() &&
            passwordsMatch

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        Text(
            text = "Registrarse",
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Campo de usuario/correo
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Usuario") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de contraseña
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de repetir contraseña
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Repetir contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = confirmPassword.isNotEmpty() && !passwordsMatch,
            supportingText = {
                if (confirmPassword.isNotEmpty() && !passwordsMatch) {
                    Text(
                        text = "Las contraseñas no coinciden",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Botón de crear cuenta
        Button(
            onClick = {
                viewModel.register(username, password, confirmPassword, "Usuario") { success ->
                    if (success) onRegisterSuccess()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4CAF50)
            ),
            enabled = !isLoading && canRegister
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Color.White
                )
            } else {
                Text("Crear cuenta", fontSize = 16.sp)
            }
        }
    }
}