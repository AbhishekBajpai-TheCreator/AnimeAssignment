package com.example.animeapp

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface JikanApiService {
    @GET("top/anime")
    suspend fun getTopAnime(): Response<AnimeResponse>
    @GET("anime/{id}")
    suspend fun getAnimeDetails(@Path("id") id: Int): Response<AnimeDetailsResponse>
}

object RetrofitClient {
    private const val BASE_URL = "https://api.jikan.moe/v4/"

    val apiService: JikanApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(JikanApiService::class.java)
    }
}
