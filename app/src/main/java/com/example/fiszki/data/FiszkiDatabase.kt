package com.example.fiszki.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Fiszka::class, Zestaw::class], version = 1, exportSchema = false)
abstract class FiszkiDatabase : RoomDatabase() {
    abstract fun fiszkaDao(): FiszkaDao
    abstract fun zestawDao(): ZestawDao

    companion object {
        @Volatile
        private var Instance: FiszkiDatabase? = null

        fun getDatabase(context: Context): FiszkiDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, FiszkiDatabase::class.java, "fiszki_database")
                    .fallbackToDestructiveMigration().build().also{ Instance = it }
            }
        }
    }
}