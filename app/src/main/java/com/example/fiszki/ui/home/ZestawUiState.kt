package com.example.fiszki.ui.home

import com.example.fiszki.data.Zestaw

data class ZestawUiState(
    val id: Int = 0,
    val name: String = ""
)

fun ZestawUiState.toZestaw(): Zestaw = Zestaw(
    zestaw_id = id,
    name = name
)

fun Zestaw.toZestawUiState(): ZestawUiState = ZestawUiState(
    id = zestaw_id,
    name = name
)

fun ZestawUiState.isValid(): Boolean {
    return name != ""
}