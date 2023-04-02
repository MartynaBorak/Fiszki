package com.example.fiszki.data

import kotlinx.coroutines.flow.Flow

class OfflineZestawyRepository(private val zestawDao: ZestawDao) : ZestawyRepository {
    override suspend fun insertZestaw(zestaw: Zestaw) = zestawDao.insert(zestaw)

    override suspend fun updateZestaw(zestaw: Zestaw) = zestawDao.update(zestaw)

    override suspend fun deleteZestaw(zestaw: Zestaw) = zestawDao.delete(zestaw)

    override fun getZestawStream(id: Int): Flow<Zestaw?> = zestawDao.getZestaw(id)
}