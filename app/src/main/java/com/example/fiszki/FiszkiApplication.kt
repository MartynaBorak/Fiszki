package com.example.fiszki

import android.app.Application

class FiszkiApplication : Application() {
    //lateinit var container: AppContainer TODO: implement AppContainer

    override fun onCreate() {
        super.onCreate()
        //container = AppDataContainer(this)
    }
}