package com.example.fiszki.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.fiszki.MainActivity
import com.example.fiszki.R

class AlarmReceiver :BroadcastReceiver(){

override fun onReceive(context: Context?, intent: Intent?) {
    val message = intent?.getStringExtra("EXTRA_MESSAGE") ?: return

    // Tworzenie kanału powiadomień (dla Androida 8.0 Oreo i nowszych)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channelId = "my_channel_id"
        val channelName = "My Channel Name"
        val channelDescription = "My Channel Description"
        val notificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel =
            NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
        channel.description = channelDescription
        notificationManager.createNotificationChannel(channel)
    }
    // Tworzenie nowego intentu dla ekranu głównego
    val mainActivityIntent = Intent(context, MainActivity::class.java)
    mainActivityIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

    // Dodawanie PendingIntent, które uruchomi aktywność ekranu głównego po kliknięciu w powiadomienie
    val pendingIntent = PendingIntent.getActivity(
        context, 0, mainActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT
    )
    // Tworzenie powiadomienia
    val notificationBuilder = NotificationCompat.Builder(context!!, "my_channel_id")
        .setSmallIcon(R.drawable.baseline_menu_book_24) // ikona powiadomienia
        .setContentTitle("Czas na naukę!") // tytuł powiadomienia
        .setContentText("Poświęć chwilę na przypomnienie fiszek") // treść powiadomienia
        .setPriority(NotificationCompat.PRIORITY_DEFAULT) // priorytet powiadomienia
        .setContentIntent(pendingIntent) //przejscie do glownego ekranu po kliknieciu w powiadomienie
        .setAutoCancel(true) //automatyczne usuwanie powiadomienia po jego odczytaniu


    // Wyświetlanie powiadomienia
    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.notify(0, notificationBuilder.build())

}
}
