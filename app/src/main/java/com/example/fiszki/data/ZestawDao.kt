package com.example.fiszki.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ZestawDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(zestaw: Zestaw)

    @Update
    suspend fun update(zestaw: Zestaw)

    @Delete
    suspend fun delete(zestaw: Zestaw)

    @Query("SELECT * from zestaw WHERE zestaw_id = :id")
    fun getZestaw(id: Int): Flow<Zestaw>

    @Query("SELECT * from zestaw ORDER BY zestaw_id ASC")
    fun getAllZestawy(): Flow<List<Zestaw>>

    @MapInfo(keyColumn = "zestaw_id", valueColumn = "cnt")
    @Query("SELECT zestaw_id, COUNT(fiszka_id) AS cnt from fiszka GROUP BY zestaw_id")
    fun getZestawyCounts(): Flow<Map<Int, Int>>
}