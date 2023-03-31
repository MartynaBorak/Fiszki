package com.example.fiszki.ui.home

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.fiszki.ui.components.FiszkiAppBar
import com.example.fiszki.ui.navigation.NavDestination
import com.example.fiszki.ui.theme.FiszkiTheme

object HomeDestination: NavDestination {
    override val route = "home"
}

@Composable
fun HomeScreen(
    navigateToZestawEntry: () -> Unit,
    navigateToZestawScreen: (Int) -> Unit,
    navigateToSettings: () -> Unit,
    //viewModel: ZestawyViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
){
    //val zestawyUiState by viewModel.zestawyUiState.collectAsState()
    Scaffold(
        topBar = {
            FiszkiAppBar(
                title = "Home - twoje zestawy",
                canNavigateBack = false,
                actiontype = "settings",
                onActionClicked = navigateToSettings
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToZestawEntry() },
                modifier = Modifier.navigationBarsPadding()
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Dodaj zestaw"
                )
            }
        }
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Text("Brak zestawów do pokazania")
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    FiszkiTheme {
        HomeScreen(
            navigateToZestawEntry = {},
            navigateToZestawScreen = {},
            navigateToSettings = {}
        )
    }
}