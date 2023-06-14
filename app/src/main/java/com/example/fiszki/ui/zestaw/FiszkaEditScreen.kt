package com.example.fiszki.ui.zestaw

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fiszki.ui.AppViewModelProvider
import com.example.fiszki.ui.navigation.NavDestination
import kotlinx.coroutines.launch

object FiszkaEditDestination: NavDestination {
    override val route = "fiszka-edit"
    const val fiszkaIdArg = "fiszkaId"
    val routeWithArgs = "$route/{$fiszkaIdArg}"
}

@Composable
fun FiszkaEditScreen(
    navigateBack: () -> Unit,
    viewModel: FiszkaEditViewModel = viewModel(factory = AppViewModelProvider.Factory),
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
                IconButton(
                    onClick = {
                        viewModel.updateUiState(fiszkaUiState.copy(isFavourite = !fiszkaUiState.isFavourite))
                    }
                ) {
                    Icon(
                        imageVector = if(fiszkaUiState.isFavourite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "Czy fiszka jest w ulubionych"
                    )
                }
            }

            OutlinedTextField(
                value = fiszkaUiState.front,
                onValueChange = { viewModel.updateUiState(fiszkaUiState.copy(front = it)) },
                singleLine = false,
                maxLines = 4,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Pierwsza strona fiszki") }
            )

            Spacer(modifier = Modifier.size(10.dp))

            OutlinedTextField(
                value = fiszkaUiState.back,
                onValueChange = { viewModel.updateUiState(fiszkaUiState.copy(back = it)) },
                singleLine = false,
                maxLines = 4,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Druga strona fiszki") }
            )

            Spacer(modifier = Modifier.size(30.dp))

            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.updateFiszka()
                        navigateBack()
                    }
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF483D8B)),
                enabled = true,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("ZAPISZ", color = Color.White, fontSize = 20.sp)
            }

            OutlinedButton(
                onClick = { navigateBack() },
                enabled = true,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("ANULUJ", color = Color(0xFF483D8B), fontSize = 20.sp)
            }
        }
    }
}