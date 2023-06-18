package com.example.fiszki.ui.learn

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fiszki.ui.AppViewModelProvider
import com.example.fiszki.ui.components.FiszkiAppBar
import com.example.fiszki.ui.navigation.NavDestination

object LearnScreenDestination: NavDestination {
    override val route = "learn"
    const val zestawIdArg = "zestawId"
    const val filteredArg = "filtered"
    val routeWithArgs = "$route/{$zestawIdArg}/{$filteredArg}"
}

@Composable
fun LearnScreen(
    navigateToSummary: () -> Unit = {},
    navigateBack: ()->Unit,
    viewModel: LearnViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
){
    val zestawyUiState = viewModel.zestawUiState
    val fiszki by viewModel.fiszkiUiState.collectAsState()

    Scaffold(
        topBar = {
            FiszkiAppBar(
                title = "Learn - setName",
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        }
    ) {paddingValues ->
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF6495ed)),
                enabled = true
            ) {
                Text("UCZ SIĘ", color = Color.White, fontSize = 20.sp)
            }
        }

    }
}