package com.example.fiszki.ui.zestaw

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fiszki.ui.AppViewModelProvider
import com.example.fiszki.ui.navigation.NavDestination
import kotlinx.coroutines.launch

object FiszkaDetailsDestination: NavDestination {
    override val route = "fiszka"
    const val fiszkaIdArg = "fiszkaId"
    val routeWithArgs = "$route/{$fiszkaIdArg}"
}

@Composable
fun FiszkaDetailsScreen(
    navigateToFiszkaEdit: (Int) -> Unit,
    navigateBack: () -> Unit,
    viewModel: FiszkaDetailsViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
) {
    val fiszkaUiState = viewModel.fiszkaUiState
    val coroutineScope = rememberCoroutineScope()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        shape = RoundedCornerShape(4.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ){
                IconButton(enabled = false, onClick = { }) {
                    Icon(
                        imageVector = if(fiszkaUiState.isFavourite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "Czy fiszka jest w ulubionych"
                    )
                }
            }

            OutlinedTextField(
                value = fiszkaUiState.front,
                enabled = false,
                onValueChange = {},
                singleLine = false,
                maxLines = 4,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Pierwsza strona fiszki") }
            )

            Spacer(modifier = Modifier.size(10.dp))

            OutlinedTextField(
                value = fiszkaUiState.back,
                enabled = false,
                onValueChange = {},
                singleLine = false,
                maxLines = 4,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Druga strona fiszki") }
            )

            Spacer(modifier = Modifier.size(30.dp))

            Button(
                onClick = { navigateToFiszkaEdit(fiszkaUiState.id) },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF483D8B)),
                enabled = true,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("EDYTUJ", color = Color.White, fontSize = 20.sp)
            }

            var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
            OutlinedButton(
                onClick = { deleteConfirmationRequired = true },
                enabled = true,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("USUŃ", color = Color(0xFF483D8B), fontSize = 20.sp)
            }

            if(deleteConfirmationRequired) {
                DeleteConfirmationDialog(
                    onDeleteConfirm = {
                        deleteConfirmationRequired = false
                        coroutineScope.launch{
                            viewModel.deleteFiszka()
                            navigateBack()
                        }
                    },
                    onDeleteCancel = { deleteConfirmationRequired = false }
                )
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
        text = { Text("Czy na pewno chcesz usunąć fiszkę?") },
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