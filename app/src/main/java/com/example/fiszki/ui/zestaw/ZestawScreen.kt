package com.example.fiszki.ui.zestaw

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fiszki.data.Fiszka
import com.example.fiszki.ui.AppViewModelProvider
import com.example.fiszki.ui.components.FiszkiAppBar
import com.example.fiszki.ui.navigation.NavDestination
import kotlinx.coroutines.launch

object ZestawScreenDestination: NavDestination {
    override val route = "zestaw"
    const val zestawIdArg = "zastawId"
    val routeWithArgs = "$route/{$zestawIdArg}"
}

@Composable
fun ZestawScreen(
    navigateToFiszkaEntry: (Int) -> Unit,
    navigateToFiszkaDetails: (Int) -> Unit,
    navigateToZestawEdit: (Int) -> Unit,
    navigateBack: () -> Unit,
    viewModel: ZestawViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
){
    // TODO: poprawić, żeby odświeżała się nazwa zestawu po edytowaniu
    val zestawUiState = viewModel.zestawUiState
    val fiszkiUiState by viewModel.fiszkiUiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            FiszkiAppBar(
                title = zestawUiState.name,
                canNavigateBack = true,
                actiontype = "menu",
                onActionClicked = { navigateToZestawEdit(zestawUiState.id) },
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToFiszkaEntry(zestawUiState.id) },
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
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                // TODO: coś do sortowania - ulubione na górze
                Checkbox(
                    checked = false,
                    onCheckedChange = { /* TODO: zaimplementować alternatywne sortowanie */ }
                )
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF483D8B)),
                    enabled = true
                ) {
                    Text("UCZ SIĘ", color = Color.White, fontSize = 20.sp)
                }
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF483D8B)),
                    enabled = true
                ) {
                    Text("GRAJ", color = Color.White, fontSize = 20.sp)
                }
            }
            Divider()

            if(fiszkiUiState.fiszkiList.isEmpty()) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    Text("Brak fiszek do pokazania")
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(paddingValues)
                ) {
                    items(
                        items = fiszkiUiState.fiszkiList,
                        key = { it.fiszka_id }
                    ) {
                        FiszkaItem(fiszka = it, onFiszkaClick = { navigateToFiszkaDetails(it.fiszka_id) })
                    }
                }
            }
        }
    }
}

@Composable
fun FiszkaItem(
    fiszka: Fiszka,
    onFiszkaClick: (Fiszka) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = 4.dp,
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { onFiszkaClick(fiszka) }
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = fiszka.front,
                fontSize = 18.sp
            )
            IconButton(onClick = { /* TODO: update isFavourite fiszki */ }) {
                Icon(
                    imageVector = if(fiszka.isFavourite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "Dodaj lub usuń z ulubionych"
                )
            }
        }
    }
}