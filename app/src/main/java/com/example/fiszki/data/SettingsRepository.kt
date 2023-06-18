package com.example.fiszki.data

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun getSwitchState(): Flow<Boolean>
    suspend fun setSwitchState(value: Boolean)
}
