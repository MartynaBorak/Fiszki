package com.example.fiszki.ui.zestaw

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fiszki.ui.AppViewModelProvider
import com.example.fiszki.ui.components.FiszkiAppBar
import com.example.fiszki.ui.navigation.NavDestination
import kotlinx.coroutines.launch

object ZestawEditDestination: NavDestination {
    override val route = "zestaw-edit"
    const val zestawIdArg = "zastawId"
    val routeWithArgs = "$route/{$zestawIdArg}"
}

@Composable
fun ZestawEditScreen(
    navigateBack: () -> Unit,
    navigateHome: () -> Unit,
    viewModel: ZestawEditViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
){
    val zestawUiState = viewModel.zestawUiState
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            FiszkiAppBar(
                // TODO: poprawić, żeby ten tytuł się nie zmianiał przed zapisaniem zmian
                title = zestawUiState.name,
                canNavigateBack = true,
                navigateUp = navigateBack
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
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                OutlinedTextField(
                    value = zestawUiState.name,
                    onValueChange = { viewModel.updateUiState(zestawUiState.copy(name=it)) },
                    label = { Text("Nazwa zestawu") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        coroutineScope.launch {
                            viewModel.updateZestaw()
                            navigateBack()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF483D8B)),
                    enabled = true,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("ZAPISZ ZMIANY", color = Color.White, fontSize = 20.sp)
                }

                OutlinedButton(
                    onClick = navigateBack,
                    enabled = true,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("ANULUJ", color = Color(0xFF483D8B), fontSize = 20.sp)
                }
            }

            Spacer(modifier = Modifier.size(20.dp))

            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Button(
                    onClick = { /* TODO: implementacja eksportu */ },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF483D8B)),
                    enabled = true,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("EKSPORTUJ DO PDF", color = Color.White, fontSize = 20.sp)
                }

                var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
                OutlinedButton(
                    onClick = { deleteConfirmationRequired = true },
                    enabled = true,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("USUŃ ZESTAW", color = Color(0xFF483D8B), fontSize = 20.sp)
                }

                if(deleteConfirmationRequired) {
                    DeleteConfirmationDialog(
                        onDeleteConfirm = {
                            deleteConfirmationRequired = false
                            coroutineScope.launch{
                                viewModel.deleteZestaw()
                                navigateHome()
                            }
                        },
                        onDeleteCancel = { deleteConfirmationRequired = false }
                    )
                }
            }
        }
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { /* Do nothing */ },
        title = { Text("UWAGA") },
        text = { Text("Czy na pewno chcesz usunąć zestaw?") },
        modifier = modifier.padding(16.dp),
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text("Nie")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text("Tak")
            }
        }
    )
}