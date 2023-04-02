package com.example.fiszki.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.background
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            Text("STWÓRZ ZESTAW", fontSize = 30.sp)

            Spacer(modifier = Modifier.size(30.dp))

            var value by remember { mutableStateOf(TextFieldValue("")) }

            BasicTextField(
                value = value,
                onValueChange = {value = it},
                decorationBox = {innerTextField -> Row(
                    Modifier.background(Color.LightGray,
                    RoundedCornerShape(percent = 10))
                    .padding(16.dp)
                        .fillMaxWidth())
                {
                    if (value.text.isEmpty()){
                        Text("Wprowadź nazwę zestawu...")
                    }
                    innerTextField()
                }
                }
            )

            Spacer(modifier = Modifier.size(150.dp))

            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF483D8B)),
                enabled = true
            ) {
                Text("ZAPISZ", color = Color.White, fontSize = 20.sp)
            }

            OutlinedButton(
                onClick = navigateBack,
                enabled = true
            ) {
                Text("ANULUJ", color = Color(0xFF483D8B), fontSize = 20.sp)
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