package com.example.fiszki.data

import kotlinx.coroutines.flow.Flow

class OfflineFiszkiRepository(private val fiszkaDao: FiszkaDao) : FiszkiRepository {
    override suspend fun insertFiszka(fiszka: Fiszka) = fiszkaDao.insert(fiszka)

    override suspend fun updateFiszka(fiszka: Fiszka) = fiszkaDao.update(fiszka)

    override suspend fun deleteFiszka(fiszka: Fiszka) = fiszkaDao.delete(fiszka)

    override fun getFiszkaStream(id: Int): Flow<Fiszka?> = fiszkaDao.getFiszka(id)

    override fun getAllInZestawStream(id: Int): Flow<List<Fiszka>> = fiszkaDao.getAllInZestaw(id)

    override fun getAllFavInZestawStream(id: Int): Flow<List<Fiszka>> = fiszkaDao.getAllFavInZestaw(id)
}