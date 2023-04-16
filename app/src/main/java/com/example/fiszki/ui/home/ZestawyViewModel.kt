package com.example.fiszki.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fiszki.data.Zestaw
import com.example.fiszki.data.ZestawyRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ZestawyViewModel(private val zestawyRepository: ZestawyRepository) : ViewModel() {
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val zestawyUiState: StateFlow<ZestawyUiState> = zestawyRepository.getAllZestawyStream()
        .map { ZestawyUiState(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = ZestawyUiState()
        )
}

data class ZestawyUiState(val zestawyList: List<Zestaw> = listOf())