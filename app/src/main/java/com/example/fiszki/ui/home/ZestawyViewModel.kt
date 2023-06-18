package com.example.fiszki.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fiszki.data.Zestaw
import com.example.fiszki.data.ZestawyRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import com.example.fiszki.ui.home.ZestawUiState
import java.lang.Thread.State

class ZestawyViewModel(private val zestawyRepository: ZestawyRepository) : ViewModel() {
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val zestawyUiState: StateFlow<ZestawyUiState> = zestawyRepository.getAllZestawyStream()
        .map { ZestawyUiState(it.map{zestaw -> zestaw.toZestawUiState()}) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = ZestawyUiState()
        )

    val zestawyCounts: StateFlow<Map<Int,Int>> = zestawyRepository.getZestawyCountsStream()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = mapOf()
        )
}
data class ZestawyUiState(val zestawyList: List<ZestawUiState> = listOf())