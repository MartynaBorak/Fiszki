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
    val filtered = viewModel.filterFav
    val fiszkiUiState by viewModel.fiszkiUiState.collectAsState()
    val favFiszkiUiState by viewModel.favFiszkiUiState.collectAsState()
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
                IconButton(onClick = { viewModel.filterFav = !filtered }) {
                    Icon(
                        imageVector = if(filtered) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "Filtruj ulubione/wszystkie"
                    )
                }
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

            if(filtered){
                if(favFiszkiUiState.fiszkiList.isEmpty()) {
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
                            items = favFiszkiUiState.fiszkiList,
                            key = { it.id }
                        ) {
                            FiszkaItem(
                                fiszka = it,
                                onFiszkaClick = { navigateToFiszkaDetails(it.id) },
                                onFavChanged = {
                                    coroutineScope.launch {
                                        viewModel.updateFiszka(it.copy(isFavourite = !it.isFavourite))
                                    }
                                }
                            )
                        }
                    }
                }
            } else {
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
                            key = { it.id }
                        ) {
                            FiszkaItem(
                                fiszka = it,
                                onFiszkaClick = { navigateToFiszkaDetails(it.id) },
                                onFavChanged = {
                                    coroutineScope.launch {
                                        viewModel.updateFiszka(it.copy(isFavourite = !it.isFavourite))
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FiszkaItem(
    fiszka: FiszkaUiState,
    onFiszkaClick: (FiszkaUiState) -> Unit,
    onFavChanged: (FiszkaUiState) -> Unit,
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
            IconButton(onClick = { onFavChanged(fiszka) }) {
                Icon(
                    imageVector = if(fiszka.isFavourite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "Dodaj lub usuń z ulubionych"
                )
            }
        }
    }
}