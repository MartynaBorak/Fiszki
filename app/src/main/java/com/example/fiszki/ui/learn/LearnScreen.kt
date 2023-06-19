package com.example.fiszki.ui.learn

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.example.fiszki.ui.zestaw.FiszkiUiState
import java.util.Collections.shuffle

object LearnScreenDestination: NavDestination {
    override val route = "learn"
    const val zestawIdArg = "zestawId"
    const val filteredArg = "filtered"
    val routeWithArgs = "$route/{$zestawIdArg}/{$filteredArg}"
}

@Composable
fun LearnScreen(
    navigateBack: ()->Unit,
    viewModel: LearnViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
){
    val zestawUiState = viewModel.zestawUiState
    val fiszki by viewModel.fiszkiUiState.collectAsState()
    var showSummaryDialog by rememberSaveable { mutableStateOf(false) }
    fiszkiShuffle(fiszki)

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
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
            ) {
                Spacer(modifier = Modifier.size(8.dp))
                if(fiszki.fiszkiList.size > viewModel.seen) {
                    Text(
                        text = "${viewModel.seen + 1}/${fiszki.fiszkiList.size}",
                        fontSize = 24.sp,
                        textAlign = TextAlign.End
                    )
                }
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = "${getScore(viewModel.correct, fiszki.fiszkiList.size)}%",
                    fontSize = 24.sp,
                    textAlign = TextAlign.End
                )
            }
            Divider()
            Spacer(modifier = Modifier.size(8.dp))
            if(fiszki.fiszkiList.isEmpty() || fiszki.fiszkiList.size > viewModel.seen){
                LearnCard(
                    if(fiszki.fiszkiList.isEmpty())
                        FiszkaUiState()
                    else
                        fiszki.fiszkiList[viewModel.seen]
                )
            }
            else showSummaryDialog = true
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
                    onClick = {
                        viewModel.seen++
                        viewModel.correct++
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF6495ed)),
                    enabled = true
                ) {
                    Text("UMIEM", color = Color.White, fontSize = 20.sp)
                }
            }

            if(showSummaryDialog) {
                SummaryDialog(
                    onRepeatClicked = {
                        showSummaryDialog = false
                        restart(viewModel, fiszki)
                    },
                    navigateBack = navigateBack,
                    score = getScore(viewModel.correct, fiszki.fiszkiList.size)
                )
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
        .clickable(onClick = { side.value = !side.value })
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

fun getScore(correct:Int, all:Int): Int {
    return if(all>0) correct*100/all else 0
}

@Composable
private fun SummaryDialog(
    onRepeatClicked: () -> Unit,
    navigateBack: () -> Unit,
    score: Int,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { navigateBack() },
        title = { Text("PODSUMOWANIE") },
        text = { Text("Twój wynik to ${score}%\nGRATULACJE!") },
        modifier = modifier.padding(16.dp),
        dismissButton = {
            TextButton(onClick = navigateBack) {
                Text("Wróć")
            }
        },
        confirmButton = {
            TextButton(onClick = onRepeatClicked) {
                Text("Jeszcze raz")
            }
        }
    )
}

fun restart(
    viewModel: LearnViewModel,
    fiszki: FiszkiUiState
) {
    viewModel.seen = 0
    viewModel.correct = 0
    fiszkiShuffle(fiszki)
}

fun fiszkiShuffle( fiszki: FiszkiUiState ) {
    shuffle(fiszki.fiszkiList)
}