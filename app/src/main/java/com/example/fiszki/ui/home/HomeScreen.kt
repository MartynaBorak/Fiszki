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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fiszki.data.Zestaw
import com.example.fiszki.ui.AppViewModelProvider
import com.example.fiszki.ui.components.FiszkiAppBar
import com.example.fiszki.ui.navigation.NavDestination
import com.example.fiszki.ui.theme.FiszkiTheme
import com.example.fiszki.ui.theme.montserratRegular
import com.example.fiszki.ui.theme.ralewayRegular

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = montserratRegular,
        fontSize = 16.sp
    )
)

val Typo = Typography(
    body2 = TextStyle(
        fontFamily = ralewayRegular,
        fontSize = 16.sp
    )
)
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
    val zestawyCounts by viewModel.zestawyCounts.collectAsState()
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
                modifier = Modifier.navigationBarsPadding(),
                backgroundColor = Color(0xFF2E8B57),
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
                Text("Brak zestawów do pokazania", style = Typography.body1, color = Color(0xff808080))
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
                    key = { it.id }
                ) {
                    ZestawItem(
                        zestaw = it,
                        onZestawClick = { navigateToZestawScreen(it.id) },
                        count = zestawyCounts[it.id]?:0
                    )
                }
            }
        }
    }
}

@Composable
fun ZestawItem(
    zestaw: ZestawUiState,
    onZestawClick: (ZestawUiState) -> Unit,
    count: Int = 0,
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
                style = Typo.body2,
                fontSize = 18.sp
            )
            Text(
                text = count.toString(),
                style = Typo.body2,
                fontSize = 18.sp
            )
        }
    }
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