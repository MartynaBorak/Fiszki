package com.example.fiszki.data

import android.content.Context

interface AppContainer {
    val fiszkiRepository: FiszkiRepository
    val zestawyRepository: ZestawyRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val fiszkiRepository: FiszkiRepository
        get() = TODO("Not yet implemented")
    override val zestawyRepository: ZestawyRepository
        get() = TODO("Not yet implemented")
}