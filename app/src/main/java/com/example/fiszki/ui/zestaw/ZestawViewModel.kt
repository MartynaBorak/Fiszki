package com.example.fiszki.ui.zestaw

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fiszki.data.FiszkiRepository
import com.example.fiszki.data.ZestawyRepository
import com.example.fiszki.ui.home.ZestawUiState
import com.example.fiszki.ui.home.toZestawUiState
import kotlinx.coroutines.flow.*

class ZestawViewModel(
    savedStateHandle: SavedStateHandle,
    private val zestawyRepository: ZestawyRepository,
    private val fiszkiRepository: FiszkiRepository
) : ViewModel() {
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    private val zestawId: Int = checkNotNull(savedStateHandle[ZestawScreenDestination.zestawIdArg])
    val zestawUiState: StateFlow<ZestawUiState> = zestawyRepository.getZestawStream(zestawId)
        .filterNotNull()
        .map {
            it.toZestawUiState()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = ZestawUiState()
        )

    // TODO: dokończyć obsługę fiszek
}