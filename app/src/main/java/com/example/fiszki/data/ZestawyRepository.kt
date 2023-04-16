package com.example.fiszki.data

import kotlinx.coroutines.flow.Flow

interface ZestawyRepository {
    suspend fun insertZestaw(zestaw: Zestaw)

    suspend fun updateZestaw(zestaw: Zestaw)

    suspend fun deleteZestaw(zestaw: Zestaw)

    fun getZestawStream(id: Int): Flow<Zestaw?>

    fun getAllZestawyStream(): Flow<List<Zestaw>>
}