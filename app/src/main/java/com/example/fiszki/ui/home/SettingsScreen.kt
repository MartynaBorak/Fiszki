package com.example.fiszki.ui.home

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.fiszki.notifications.AlarmItem
import com.example.fiszki.notifications.AndroidAlarmScheduler
import com.example.fiszki.ui.components.FiszkiAppBar
import com.example.fiszki.ui.navigation.NavDestination
import com.example.fiszki.ui.theme.FiszkiTheme
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*



object SettingsDestination: NavDestination {
    override val route = "settings"
}



@Composable
fun SettingsScreen(


    navigateUp: () -> Unit,
    //viewModel: SettingsViewModel = viewModel(factory = AppViewModelProvider.Factory),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    //timePicker

    // deklaracja i inicjalizacja kalendarza
    val mCalendar = Calendar.getInstance()
    val mHour = mCalendar[Calendar.HOUR_OF_DAY]
    val mMinute = mCalendar[Calendar.MINUTE]

    // val do przechowywania czasu
    val mTime = remember { mutableStateOf("") }

    // utworzenie dialogu time pickera
    val mTimePickerDialog = TimePickerDialog(
        context,
        {_, mHour : Int, mMinute: Int ->
            mTime.value = "$mHour:$mMinute"
        }, mHour, mMinute, false
    )

    //

    //var timeText by remember { mutableStateOf("") }
    val scheduler = AndroidAlarmScheduler(context)
    var alarmItem: AlarmItem? = null

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
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.size(104.dp))

            Text("PRZYPOMNIENIA", fontSize = 28.sp)

            val checkedState = remember {
                mutableStateOf(false)
            }

            Switch(
                checked = checkedState.value,
                onCheckedChange = { checkedState.value = it },
                colors = SwitchDefaults.colors(Color.Green)
            )

            if (checkedState.value) {

                Spacer(modifier = Modifier.size(48.dp))

                // Po naciśnięciu guzika wyświetla się time picker
                Button(
                    onClick = { mTimePickerDialog.show() },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFFADD8E6))
                ) {
                    Text(text = "GODZINA PRZYPOMNIEŃ", color = Color.Black, fontSize = 20.sp)
                }


                // Wyświetlanie czasu wybranego przez użytkownika
                Text(text = "Wybrany czas: ${mTime.value}", fontSize = 20.sp)

                Spacer(modifier = Modifier.size(48.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            val currentTime = LocalTime.now()
                            val alarmTime = LocalTime.parse(mTime.value)
                            val triggerTime = if (alarmTime.isBefore(currentTime)) {
                                alarmTime.plusHours(24)
                            } else {
                                alarmTime
                            }
                            val triggerDateTime = LocalDateTime.of(
                                LocalDateTime.now().toLocalDate(),
                                triggerTime
                            )
                            alarmItem = AlarmItem(
                                time = triggerDateTime,
                                message = ""
                            )
                            alarmItem?.let(scheduler::schedule)

                            Toast.makeText(context, "Ustawienia zapisane", Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF483D8B))
                    ) {
                        Text(text = "ZAPISZ", color = Color.White, fontSize = 20.sp)
                    }

                    Spacer(modifier = Modifier.size(16.dp))

                    Button(
                        onClick = {
                            alarmItem?.let(scheduler::cancel)
                            Toast.makeText(context, "Przypomnienie anulowane", Toast.LENGTH_SHORT).show()
                            mTime.value=""
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF483D8B))
                    ) {
                        Text(text = "ANULUJ", color = Color.White, fontSize = 20.sp)
                    }
                }
            }else{
                alarmItem?.let(scheduler::cancel)
                mTime.value=""
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