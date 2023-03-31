package com.example.fiszki.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.fiszki.ui.components.FiszkiAppBar
import com.example.fiszki.ui.navigation.NavDestination
import com.example.fiszki.ui.theme.FiszkiTheme

object SettingsDestination: NavDestination {
    override val route = "settings"
}

@Composable
fun SettingsScreen(
    navigateUp: () -> Unit,
    //viewModel: SettingsViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
){
    //val settingsUiState by viewModel.settingsUiState.collectAsState()
    Scaffold(
        topBar = {
            FiszkiAppBar(
                title = "Ustawienia",
                canNavigateBack = true,
                navigateUp = navigateUp
            )
        }
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Text("To jest ekran ustawień")
            Button(
                onClick = { },
                enabled = true
            ) {
                Text("Zapisz")
            }
        }
    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    FiszkiTheme {
        SettingsScreen(navigateUp = { })
    }
}