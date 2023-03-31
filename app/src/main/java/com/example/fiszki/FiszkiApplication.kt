package com.example.fiszki

import android.app.Application
import com.example.fiszki.data.AppContainer
import com.example.fiszki.data.AppDataContainer

class FiszkiApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}