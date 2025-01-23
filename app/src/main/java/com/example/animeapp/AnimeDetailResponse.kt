package com.example.animeapp

data class AnimeDetailsResponse(
    val data: AnimeDetails
)

data class AnimeDetails(
    val mal_id: Int,
    val title: String,
    val synopsis: String,
    val episodes: Int?,
    val score: Double?,
    val genres: List<Genre>?,
    val characters: List<Character>?,
    val images: ImageWrapperr,
    val trailer: Trailer
)

data class Genre(
    val name: String
)

data class Character(
    val name: String
)

data class Trailer(
    val youtube_id: String?
)

data class ImageWrapperr(
    val jpg: LargeImageDetails
)

data class LargeImageDetails(
    val large_image_url: String
)