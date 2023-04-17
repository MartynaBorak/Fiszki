package com.example.fiszki.ui.zestaw

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fiszki.data.FiszkiRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class FiszkaDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val fiszkiRepository: FiszkiRepository
) : ViewModel() {
    private val fiszkaId: Int = checkNotNull(savedStateHandle[FiszkaDetailsDestination.fiszkaIdArg])
    var fiszkaUiState by mutableStateOf(FiszkaUiState())
        private set

    init {
        viewModelScope.launch {
            fiszkaUiState = fiszkiRepository.getFiszkaStream(fiszkaId)
                .filterNotNull()
                .first()
                .toFiszkaUiState()
        }
    }

    suspend fun deleteFiszka() {
        fiszkiRepository.deleteFiszka(fiszkaUiState.toFiszka())
    }
}