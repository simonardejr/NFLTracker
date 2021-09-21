package com.example.aula1

import android.app.Application
import androidx.room.Room
import com.example.aula1.model.AppDatabase

class MyApplication: Application() {

    companion object {
        var database: AppDatabase? = null
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "games_db")
            .fallbackToDestructiveMigration()
            .build()
    }
}