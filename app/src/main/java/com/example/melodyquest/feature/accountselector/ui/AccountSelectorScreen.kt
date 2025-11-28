package com.example.melodyquest.feature.accountselector.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.melodyquest.R
import com.example.melodyquest.core.ui.icons.AppIcons
import com.example.melodyquest.core.ui.icons.Plus
import com.example.melodyquest.core.ui.icons.User

// Data class para representar una cuenta de usuario
data class UserAccount(val id: String, val name: String, val avatarResId: Int)

// Datos de ejemplo. En una app real, esto vendría de una base de datos local.
val sampleAccounts = listOf(
    UserAccount("1", "Elena", R.drawable.ic_launcher_background),
    UserAccount("2", "Carlos", R.drawable.ic_launcher_background)
)

@Composable
fun AccountSelectorScreen(
    accounts: List<UserAccount>,
    onAccountSelected: (UserAccount) -> Unit,
    onAddAccountClicked: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surface
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Seleccionar cuenta",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(32.dp))
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(accounts) { account ->
                    AccountItem(account = account, onClick = { onAccountSelected(account) })
                }
                item {
                    AddAccountItem(onClick = onAddAccountClicked)
                }
            }
        }
    }
}

@Composable
fun AccountItem(account: UserAccount, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                imageVector = AppIcons.User,
                contentDescription = "Avatar de ${account.name}",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = account.name, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
fun AddAccountItem(onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = AppIcons.Plus,
                contentDescription = "Agregar cuenta",
                modifier = Modifier
                    .size(40.dp)
                    .padding(4.dp),
                tint = Color.Gray
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = "Agregar cuenta", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Preview(
    showBackground = true

)
@Composable
fun AccountSelectorScreenPreview() {
    AccountSelectorScreen(
        accounts = sampleAccounts,
        onAccountSelected = {},
        onAddAccountClicked = {}
    )
}
