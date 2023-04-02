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
}