package com.example.fiszki.ui.home

import android.app.TimePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.fiszki.ui.components.FiszkiAppBar
import com.example.fiszki.ui.navigation.NavDestination
import com.example.fiszki.ui.theme.FiszkiTheme
import java.util.*



object SettingsDestination: NavDestination {
    override val route = "settings"
}

@Composable
fun SettingsScreen(


    navigateUp: () -> Unit,
    //viewModel: SettingsViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
){
    //timePicker
    val mContext = LocalContext.current

    // deklaracja i inicjalizacja kalendarza
    val mCalendar = Calendar.getInstance()
    val mHour = mCalendar[Calendar.HOUR_OF_DAY]
    val mMinute = mCalendar[Calendar.MINUTE]

    // val do przechowywania czasu
    val mTime = remember { mutableStateOf("") }

    // utworzenie dialogu time pickera
    val mTimePickerDialog = TimePickerDialog(
        mContext,
        {_, mHour : Int, mMinute: Int ->
            mTime.value = "$mHour:$mMinute"
        }, mHour, mMinute, false
    )
    //
    //val settingsUiState by viewModel.settingsUiState.collectAsState()
    Scaffold(
        topBar = {
            FiszkiAppBar(
                title = "Ustawienia",
                canNavigateBack = true,
                navigateUp = navigateUp
            )
        }
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Text("Przypomnienia",fontSize = 20.sp)


            //SWITCH

            val checkedState = remember {
                mutableStateOf(false)
            }

            Switch(
                checked = checkedState.value,
                onCheckedChange = { checkedState.value = it },
                colors = SwitchDefaults.colors(Color.Green)
            )

            if (checkedState.value) {

                Spacer(modifier = Modifier.size(50.dp))

                // Po naciśnięciu guzika wyświetla się time picker
                Button(
                    onClick = { mTimePickerDialog.show() },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFFADD8E6))
                ) {
                    Text(text = "GODZINA PRZYPOMNIEŃ", color = Color.Black, fontSize = 20.sp)
                }


                // Wyświetlanie czasu wybranego przez użytkownika
                Text(text = "Wybrany czas: ${mTime.value}", fontSize = 20.sp)
            }


            Spacer(modifier = Modifier.size(150.dp))

            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF483D8B)),
                enabled = true
            ) {
                Text("ZAPISZ", color = Color.White, fontSize = 20.sp)
            }
        }
    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    FiszkiTheme {
        SettingsScreen(navigateUp = { })
    }
}