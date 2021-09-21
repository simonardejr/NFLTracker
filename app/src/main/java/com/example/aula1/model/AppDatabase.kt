package com.example.aula1.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.aula1.model.dao.GameDao

@Database(entities = [Games::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun GameDao(): GameDao
}