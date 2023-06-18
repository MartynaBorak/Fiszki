package com.example.fiszki.ui.learn

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fiszki.data.FiszkiRepository
import com.example.fiszki.data.ZestawyRepository
import com.example.fiszki.ui.home.ZestawUiState
import com.example.fiszki.ui.home.toZestawUiState
import com.example.fiszki.ui.zestaw.FiszkiUiState
import com.example.fiszki.ui.zestaw.ZestawViewModel
import com.example.fiszki.ui.zestaw.toFiszkaUiState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class LearnViewModel(
    savedStateHandle: SavedStateHandle,
    private val zestawyRepository: ZestawyRepository,
    private val fiszkiRepository: FiszkiRepository
) : ViewModel() {
    private val zestawId: Int = checkNotNull(savedStateHandle[LearnScreenDestination.zestawIdArg])
    private val filtered: Boolean = checkNotNull(savedStateHandle[LearnScreenDestination.filteredArg])
    var zestawUiState by mutableStateOf(ZestawUiState())
        private set

    init{
        viewModelScope.launch {
            zestawUiState = zestawyRepository.getZestawStream(zestawId)
                .filterNotNull()
                .first()
                .toZestawUiState()
        }
    }

    companion object{
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val fiszkiUiState: StateFlow<FiszkiUiState> =
        if(filtered) {
            fiszkiRepository.getAllFavInZestawStream(zestawId)
                .map { FiszkiUiState(it.map { fiszka -> fiszka.toFiszkaUiState() }) }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(LearnViewModel.TIMEOUT_MILLIS),
                    initialValue = FiszkiUiState()
                )
        } else {
            fiszkiRepository.getAllInZestawStream(zestawId)
                .map { FiszkiUiState(it.map { fiszka -> fiszka.toFiszkaUiState()  }) }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(LearnViewModel.TIMEOUT_MILLIS),
                    initialValue = FiszkiUiState()
                )
        }

    var count = fiszkiUiState.value.fiszkiList.count()
    var seen by mutableStateOf(0)
}