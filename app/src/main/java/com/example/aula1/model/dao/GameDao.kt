package com.example.aula1.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.aula1.model.Games

@Dao
interface GameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllGames(games: List<Games>)

    // @Query("SELECT * FROM Games LIMIT :page:")
    // fun getPaginatedGames(page: Int)

    @get:Query("SELECT DISTINCT * FROM Games")
    val games: LiveData<List<Games>>

    @Query("DELETE FROM Games")
    fun deleteAll()
}