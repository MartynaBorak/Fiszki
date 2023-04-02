package com.example.fiszki.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.fiszki.FiszkiApplication

object AppViewModelProvider {
    val Factory = viewModelFactory {
        //Initializers for each VM
        // TODO: VMs not implemented yet
    }
}

fun CreationExtras.FiszkiApplication(): FiszkiApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as FiszkiApplication)