package com.example.fiszki.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "fiszka",
    foreignKeys = arrayOf(ForeignKey(
        entity = Zestaw::class,
                parentColumns = arrayOf("zestaw_id"),
                childColumns = arrayOf("zestaw_id"),
                onDelete = ForeignKey.CASCADE
    ))
)
data class Fiszka (
    @PrimaryKey(autoGenerate = true)
    val fiszka_id: Int = 0,
    val front: String,
    val back: String,
    val isFavourite: Boolean,
    val zestaw_id: Int
)