package com.example.fiszki.ui.zestaw

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fiszki.data.ZestawyRepository
import com.example.fiszki.ui.home.ZestawUiState
import com.example.fiszki.ui.home.isValid
import com.example.fiszki.ui.home.toZestaw
import com.example.fiszki.ui.home.toZestawUiState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ZestawEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val zestawyRepository: ZestawyRepository
) : ViewModel() {
    private val zestawId: Int = checkNotNull(savedStateHandle[ZestawScreenDestination.zestawIdArg])
    var zestawUiState by mutableStateOf(ZestawUiState())
        private set

    init {
        viewModelScope.launch {
            zestawUiState = zestawyRepository.getZestawStream(zestawId)
                .filterNotNull()
                .first()
                .toZestawUiState()
        }
    }

    fun updateUiState(newZestawUiState: ZestawUiState) {
        zestawUiState = newZestawUiState.copy()
    }

    suspend fun updateZestaw() {
        if(zestawUiState.isValid()){
            zestawyRepository.updateZestaw(zestawUiState.toZestaw())
        }
    }

    suspend fun deleteZestaw() {
        zestawyRepository.deleteZestaw(zestawUiState.toZestaw())
    }
}