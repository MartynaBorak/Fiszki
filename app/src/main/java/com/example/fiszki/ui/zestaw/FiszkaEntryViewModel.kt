package com.example.fiszki.ui.zestaw

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.fiszki.data.FiszkiRepository

class FiszkaEntryViewModel(private val fiszkiRepository: FiszkiRepository) : ViewModel() {
    var fiszkaUiState by mutableStateOf(FiszkaUiState())
        private set

    fun updateUiState(newFiszkaUiState: FiszkaUiState) {
        fiszkaUiState = newFiszkaUiState.copy()
    }

    suspend fun saveFiszka(zestawId: Int) {
        if(fiszkaUiState.isValid()){
            fiszkiRepository.insertFiszka(
                fiszkaUiState.copy(zestawId = zestawId).toFiszka()
            )
        }
    }
}