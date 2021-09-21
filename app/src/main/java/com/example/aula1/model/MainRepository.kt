package com.example.aula1.model

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.aula1.MyApplication
import com.example.aula1.network.NFLAPI
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainRepository {
    private val api: NFLAPI = Retrofit.Builder()
        //.baseUrl("http://192.168.0.199:3001")
        .baseUrl("http://api.minhacasa.rocks")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NFLAPI::class.java)

    fun getGamesFromDb(): LiveData<List<Games>> {
        return MyApplication.database!!.GameDao().games
    }

    suspend fun getGamesFromApi(): Response<List<Games>> {
        Log.d("responseapi", api.fetchAllGames().toString())
        return api.fetchAllGames()
    }
}