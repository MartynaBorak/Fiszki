package com.example.fiszki.data

import android.content.Context

interface AppContainer {
    val fiszkiRepository: FiszkiRepository
    val zestawyRepository: ZestawyRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val fiszkiRepository: FiszkiRepository by lazy {
        OfflineFiszkiRepository(FiszkiDatabase.getDatabase(context).fiszkaDao())
    }

    override val zestawyRepository: ZestawyRepository by lazy {
        OfflineZestawyRepository(FiszkiDatabase.getDatabase(context).zestawDao())
    }

}