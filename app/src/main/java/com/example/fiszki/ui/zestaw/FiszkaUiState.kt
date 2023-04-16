package com.example.fiszki.ui.zestaw

import com.example.fiszki.data.Fiszka

data class FiszkaUiState(
    val id: Int = 0,
    val front: String = "",
    val back: String = "",
    val isFavourite: Boolean = false,
    val zestawId: Int = 0
)

fun FiszkaUiState.toFiszka(): Fiszka = Fiszka(
    fiszka_id = id,
    front = front,
    back = back,
    isFavourite = isFavourite,
    zestaw_id = zestawId
)

fun Fiszka.toFiszkaUiState(): FiszkaUiState = FiszkaUiState(
    id = fiszka_id,
    front = front,
    back = back,
    isFavourite = isFavourite,
    zestawId = zestaw_id
)

fun FiszkaUiState.isValid(): Boolean {
    return front != "" && back != ""
}