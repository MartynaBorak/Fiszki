package com.example.fiszki.ui.zestaw

import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fiszki.ui.AppViewModelProvider
import com.example.fiszki.ui.components.FiszkiAppBar
import com.example.fiszki.ui.home.ZestawyViewModel
import com.example.fiszki.ui.navigation.NavDestination

object ZestawScreenDestination: NavDestination {
    override val route = "zestaw"
    const val zestawIdArg = "zastawId"
    val routeWithArgs = "$route/{$zestawIdArg}"
}

@Composable
fun ZestawScreen(
    navigateToFiszkaEntry: () -> Unit,
    navigateToFiszkaDetails: (Int) -> Unit,
    navigateToZestawEdit: (Int) -> Unit,
    navigateBack: () -> Unit,
    viewModel: ZestawViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
){
    val zestawUiState = viewModel.zestawUiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            FiszkiAppBar(
                title = zestawUiState.value.name,
                canNavigateBack = true,
                actiontype = "menu",
                onActionClicked = { navigateToZestawEdit(zestawUiState.value.id) },
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToFiszkaEntry() },
                modifier = Modifier.navigationBarsPadding()
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Dodaj fiszkę"
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
            Text("Brak fiszek do pokazania")
        }
    }
}