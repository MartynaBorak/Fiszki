package com.example.fiszki.ui.learn

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fiszki.ui.AppViewModelProvider
import com.example.fiszki.ui.components.FiszkiAppBar
import com.example.fiszki.ui.navigation.NavDestination
import com.example.fiszki.ui.zestaw.FiszkaUiState

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
    val zestawUiState = viewModel.zestawUiState
    val fiszki by viewModel.fiszkiUiState.collectAsState()

    Scaffold(
        topBar = {
            FiszkiAppBar(
                title = "Learn - ${zestawUiState.name}",
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        }
    ) {paddingValues ->
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
            ) {
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = "${viewModel.seen}/${fiszki.fiszkiList.size}",
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.size(8.dp))
            }
            Divider()
            Spacer(modifier = Modifier.size(8.dp))
            LearnCard(
                if(fiszki.fiszkiList.isEmpty())
                    FiszkaUiState()
                else if(fiszki.fiszkiList.size <= viewModel.seen)
                    FiszkaUiState()
                else
                    fiszki.fiszkiList[viewModel.seen])
            Spacer(modifier = Modifier.size(8.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { viewModel.seen++ },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF6495ed)),
                    enabled = true
                ) {
                    Text("NIE UMIEM", color = Color.White, fontSize = 20.sp)
                }
                Spacer(modifier = Modifier.size(16.dp))
                Button(
                    onClick = { viewModel.seen++ }, //TODO: dodac liczenie wyniku
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF6495ed)),
                    enabled = true
                ) {
                    Text("UMIEM", color = Color.White, fontSize = 20.sp)
                }
            }

        }

    }
}

@Composable
fun LearnCard(
    fiszka: FiszkaUiState,
    modifier: Modifier = Modifier
){
    val side = remember { mutableStateOf(true) }
    Column(modifier = Modifier
        .padding(30.dp)
        .fillMaxWidth()
        .clickable(onClick = { side.value = !side.value } )
        .clip(shape = RoundedCornerShape(16.dp)),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .border(width = 4.dp, color = Color.Gray, shape = RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if(side.value) fiszka.front else fiszka.back,
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center,
                fontSize = 24.sp
            )
        }
    }
}