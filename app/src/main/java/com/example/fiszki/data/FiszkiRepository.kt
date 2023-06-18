package com.example.fiszki.data

import kotlinx.coroutines.flow.Flow

interface FiszkiRepository {
    suspend fun insertFiszka(fiszka: Fiszka)

    suspend fun updateFiszka(fiszka: Fiszka)

    suspend fun deleteFiszka(fiszka: Fiszka)

    fun getFiszkaStream(id: Int): Flow<Fiszka?>

    fun getAllInZestawStream(id: Int): Flow<List<Fiszka>>

    fun getAllFavInZestawStream(id: Int): Flow<List<Fiszka>>
}