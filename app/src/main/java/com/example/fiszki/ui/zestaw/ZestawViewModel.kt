package com.example.fiszki.ui.zestaw

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fiszki.data.Fiszka
import com.example.fiszki.data.FiszkiRepository
import com.example.fiszki.data.ZestawyRepository
import com.example.fiszki.ui.home.ZestawUiState
import com.example.fiszki.ui.home.toZestawUiState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ZestawViewModel(
    savedStateHandle: SavedStateHandle,
    private val zestawyRepository: ZestawyRepository,
    private val fiszkiRepository: FiszkiRepository
) : ViewModel() {
    private val zestawId: Int = checkNotNull(savedStateHandle[ZestawScreenDestination.zestawIdArg])
    var zestawUiState by mutableStateOf(ZestawUiState())
        private set
    var filterFav by mutableStateOf(false)
    init {
        viewModelScope.launch {
            zestawUiState = zestawyRepository.getZestawStream(zestawId)
                .filterNotNull()
                .first()
                .toZestawUiState()
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val fiszkiUiState: StateFlow<FiszkiUiState> = fiszkiRepository.getAllInZestawStream(zestawId)
        .map { FiszkiUiState(it.map { fiszka -> fiszka.toFiszkaUiState()  }) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = FiszkiUiState()
        )

    val favFiszkiUiState: StateFlow<FiszkiUiState> = fiszkiRepository.getAllFavInZestawStream(zestawId)
        .map { FiszkiUiState(it.map { fiszka -> fiszka.toFiszkaUiState() }) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = FiszkiUiState()
        )

    suspend fun updateFiszka(fiszka: FiszkaUiState) {
        if(fiszka.isValid()){
            fiszkiRepository.updateFiszka(fiszka.toFiszka())
        }
    }
}

data class FiszkiUiState(val fiszkiList: List<FiszkaUiState> = listOf())