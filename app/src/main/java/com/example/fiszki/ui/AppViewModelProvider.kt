package com.example.fiszki.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.fiszki.FiszkiApplication
import com.example.fiszki.ui.home.ZestawEntryViewModel
import com.example.fiszki.ui.home.ZestawyViewModel
import com.example.fiszki.ui.learn.LearnViewModel
import com.example.fiszki.ui.zestaw.*

object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for ZestawyVM - home screen list of sets
        initializer {
            ZestawyViewModel(
                FiszkiApplication().container.zestawyRepository
            )
        }

        // Initializer for ZestawEntryVM
        initializer {
            ZestawEntryViewModel(
                FiszkiApplication().container.zestawyRepository
            )
        }

        // Initializer for ZestawVM
        initializer {
            ZestawViewModel(
                this.createSavedStateHandle(),
                FiszkiApplication().container.zestawyRepository,
                FiszkiApplication().container.fiszkiRepository
            )
        }

        // Initializer for ZestawEditVM
        initializer {
            ZestawEditViewModel(
                this.createSavedStateHandle(),
                FiszkiApplication().container.zestawyRepository
            )
        }

        // Initializer for FiszkaEntryVM
        initializer {
            FiszkaEntryViewModel(
                FiszkiApplication().container.fiszkiRepository
            )
        }

        // Initializer for FiszkaDetailsVM
        initializer {
            FiszkaDetailsViewModel(
                this.createSavedStateHandle(),
                FiszkiApplication().container.fiszkiRepository
            )
        }

        // Initializer for FiszkaEditVM
        initializer {
            FiszkaEditViewModel(
                this.createSavedStateHandle(),
                FiszkiApplication().container.fiszkiRepository
            )
        }

        // Initializer for LearnVM
        initializer {
            LearnViewModel(
                this.createSavedStateHandle(),
                FiszkiApplication().container.zestawyRepository,
                FiszkiApplication().container.fiszkiRepository
            )
        }
    }
}

fun CreationExtras.FiszkiApplication(): FiszkiApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as FiszkiApplication)