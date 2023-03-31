package com.example.fiszki.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fiszki.ui.navigation.NavDestination
import com.example.fiszki.ui.theme.FiszkiTheme

object ZestawEntryDestination: NavDestination {
    override val route = "zestaw-entry"
}

@Composable
fun ZestawEntryScreen(
    navigateBack: () -> Unit,
    //viewModel: ZestawEntryViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
){
    //val zestawUiState by viewModel.zestawUiState.collectAsState()
    //val coroutineScope = rememberCoroutineScope()
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
            Text("To jest ekran dodawania zestawu")
            Button(
                onClick = { },
                enabled = true
            ) {
                Text("Zapisz")
            }
            OutlinedButton(
                onClick = navigateBack,
                enabled = true
            ) {
                Text("Anuluj")
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