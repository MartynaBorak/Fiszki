package com.example.fiszki.ui.zestaw

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fiszki.ui.AppViewModelProvider
import com.example.fiszki.ui.navigation.NavDestination
import kotlinx.coroutines.launch

object FiszkaEntryDestination: NavDestination {
    override val route = "fiszka-entry"
}

@Composable
fun FiszkaEntryScreen(
    navigateBack: () -> Unit,
    zestawId: Int,
    viewModel: FiszkaEntryViewModel = viewModel(factory = AppViewModelProvider.Factory),
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
            Text("STWÓRZ FISZKĘ", fontSize = 30.sp)

            Spacer(modifier = Modifier.size(30.dp))

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
                        viewModel.saveFiszka(zestawId)
                        navigateBack()
                    }
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF2E8B57)),
                enabled = true
            ) {
                Text("ZAPISZ", color = Color.White, fontSize = 20.sp)
            }

            OutlinedButton(
                onClick = navigateBack,
                enabled = true
            ) {
                Text("ANULUJ", color = Color(0xFFFF6347), fontSize = 20.sp)
            }
        }

    }
}