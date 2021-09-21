package com.example.aula1.network

import com.example.aula1.model.Games
import retrofit2.Response
import retrofit2.http.GET

interface NFLAPI {
    @GET("/kljhasdi87q62nsdhaskjdha98s72kjhelkwh2/iesb")
    suspend fun fetchAllGames(): Response<List<Games>>
}