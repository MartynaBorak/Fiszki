package com.example.fiszki.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FiszkaDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(fiszka: Fiszka)

    @Update
    suspend fun update(fiszka: Fiszka)

    @Delete
    suspend fun delete(fiszka: Fiszka)

    @Query("SELECT * from fiszka WHERE fiszka_id = :id")
    fun getFiszka(id: Int): Flow<Fiszka>

    @Query("SELECT * from fiszka WHERE zestaw_id = :id ORDER BY front ASC")
    fun getAllInZestaw(id: Int): Flow<List<Fiszka>>

    @Query("SELECT * from fiszka WHERE zestaw_id = :id AND isFavourite = true ORDER BY front ASC")
    fun getAllFavInZestaw(id: Int): Flow<List<Fiszka>>
}