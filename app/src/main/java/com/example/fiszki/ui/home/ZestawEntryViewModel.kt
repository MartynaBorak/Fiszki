package com.example.fiszki.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.fiszki.data.ZestawyRepository

class ZestawEntryViewModel(private val zestawyRepository: ZestawyRepository) : ViewModel() {
    var zestawUiState by mutableStateOf(ZestawUiState())
        private set

    fun updateUiState(newZestawUiState: ZestawUiState) {
        zestawUiState = newZestawUiState.copy()
    }

    suspend fun saveZestaw() {
        if(zestawUiState.isValid()){
            zestawyRepository.insertZestaw(zestawUiState.toZestaw())
        }
    }
}