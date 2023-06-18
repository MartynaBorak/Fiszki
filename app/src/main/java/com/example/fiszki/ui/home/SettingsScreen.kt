package com.example.fiszki.ui.home

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import androidx.preference.PreferenceManager
import com.example.fiszki.notifications.AlarmItem
import com.example.fiszki.notifications.AndroidAlarmScheduler
import com.example.fiszki.ui.components.FiszkiAppBar
import com.example.fiszki.ui.navigation.NavDestination
import com.example.fiszki.ui.theme.FiszkiTheme
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeParseException
import java.util.*

object SettingsDestination: NavDestination {
    override val route = "settings"
}

@Composable
fun SettingsScreen(
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    // Deklaracja i inicjalizacja kalendarza
    val mCalendar = Calendar.getInstance()
    val mHour = mCalendar[Calendar.HOUR_OF_DAY]
    val mMinute = mCalendar[Calendar.MINUTE]

    // Val do przechowywania czasu
    val mTime = remember { mutableStateOf("") }

    // Utworzenie dialogu TimePicker
    val mTimePickerDialog = TimePickerDialog(
        context,
        { _, mHour: Int, mMinute: Int ->
            mTime.value = String.format("%02d:%02d", mHour, mMinute)
        }, mHour, mMinute, false
    )

    val scheduler = AndroidAlarmScheduler(context)
    var alarmItem: AlarmItem? = null

    if (sharedPreferences.contains("reminder_time")) {
        val savedTime = sharedPreferences.getString("reminder_time", "")
        if (savedTime != null && savedTime.isNotEmpty()) {
            mTime.value = savedTime
        }
    }

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
            Spacer(modifier = Modifier.size(48.dp))
            // Po naciśnięciu guzika wyświetla się TimePicker
            Button(
                onClick = { mTimePickerDialog.show() },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFF8FBC8F))
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
                        if (mTime.value.isBlank()) {
                            Toast.makeText(context, "Nie wybrano godziny", Toast.LENGTH_SHORT).show()
                        } else {
                            val currentTime = LocalTime.now()
                            val alarmTime = try {
                                LocalTime.parse(mTime.value)
                            } catch (ex: DateTimeParseException) {
                                null
                            }
                            if (alarmTime != null) {
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
                                sharedPreferences.edit().putString("reminder_time", mTime.value).apply()
                                Toast.makeText(context, "Ustawienia zapisane", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, "Nieprawidłowy format godziny", Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF2E8B57))
                ) {
                    Text(text = "ZAPISZ", color = Color.White, fontSize = 20.sp)
                }
                Spacer(modifier = Modifier.size(16.dp))
                Button(
                    onClick = {
                        alarmItem?.let(scheduler::cancel)
                        Toast.makeText(context, "Przypomnienie anulowane", Toast.LENGTH_SHORT).show()
                        mTime.value = ""
                        sharedPreferences.edit().remove("reminder_time").apply()
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF6347))
                ) {
                    Text(text = "ANULUJ", color = Color.White, fontSize = 20.sp)
                }
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
