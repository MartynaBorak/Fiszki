package com.example.fiszki.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fiszki.ui.AppViewModelProvider
import com.example.fiszki.ui.navigation.NavDestination
import com.example.fiszki.ui.theme.FiszkiTheme
import kotlinx.coroutines.launch


object ZestawEntryDestination: NavDestination {
    override val route = "zestaw-entry"
}

@Composable
fun ZestawEntryScreen(
    navigateBack: () -> Unit,
    viewModel: ZestawEntryViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
){
    val zestawUiState = viewModel.zestawUiState
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
            Text("STWÓRZ ZESTAW", fontSize = 30.sp)

            Spacer(modifier = Modifier.size(30.dp))

            OutlinedTextField(
                value = zestawUiState.name,
                onValueChange = { viewModel.updateUiState(zestawUiState.copy(name=it)) },
                label = { Text("Nazwa zestawu") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.size(150.dp))

            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.saveZestaw()
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

@Preview
@Composable
fun ZestawEntryScreenPreview() {
    FiszkiTheme {
        ZestawEntryScreen(navigateBack = { })
    }
}