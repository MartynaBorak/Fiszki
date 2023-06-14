package com.example.fiszki.ui.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fiszki.data.Zestaw
import com.example.fiszki.ui.AppViewModelProvider
import com.example.fiszki.ui.components.FiszkiAppBar
import com.example.fiszki.ui.navigation.NavDestination
import com.example.fiszki.ui.theme.FiszkiTheme

object HomeDestination: NavDestination {
    override val route = "home"
}

@Composable
fun HomeScreen(
    navigateToZestawEntry: () -> Unit,
    navigateToZestawScreen: (Int) -> Unit,
    navigateToSettings: () -> Unit,
    viewModel: ZestawyViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
){
    val zestawyUiState by viewModel.zestawyUiState.collectAsState()
    Scaffold(
        topBar = {
            FiszkiAppBar(
                title = "Home - twoje zestawy",
                canNavigateBack = false,
                actiontype = "settings",
                onActionClicked = navigateToSettings
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToZestawEntry() },
                modifier = Modifier.navigationBarsPadding()
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Dodaj zestaw"
                )
            }
        }
    ) { paddingValues ->
        if(zestawyUiState.zestawyList.isEmpty()) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Text("Brak zestawów do pokazania")
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxWidth()
            ) {
                items(
                    items = zestawyUiState.zestawyList,
                    key = { it.zestaw_id }
                ) {
                    ZestawItem(zestaw = it, onZestawClick = { navigateToZestawScreen(it.zestaw_id) })
                }
            }
        }
    }
}

@Composable
fun ZestawItem(
    zestaw: Zestaw,
    onZestawClick: (Zestaw) -> Unit,
    modifier: Modifier = Modifier
){
    Card(
        elevation = 4.dp,
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { onZestawClick(zestaw) }
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = zestaw.name,
                fontSize = 18.sp
            )
            Text(
                text = fiszkiCount(zestaw).toString(),
                fontSize = 18.sp
            )
        }
    }
}

fun fiszkiCount(zestaw: Zestaw): Int {
    //TODO: napisać zapytanie o liczbę fiszek w zestawie
    return 0
}

@Preview
@Composable
fun HomeScreenPreview() {
    FiszkiTheme {
        HomeScreen(
            navigateToZestawEntry = {},
            navigateToZestawScreen = {},
            navigateToSettings = {}
        )
    }
}