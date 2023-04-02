package com.example.fiszki.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "zestaw")
data class Zestaw (
    @PrimaryKey(autoGenerate = true)
    val zestaw_id: Int = 0,
    val name: String
)