package com.example.animeapp

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class AnimeResponse(
    val data: List<Anime>
) : Parcelable

@Parcelize
data class Anime(
    val mal_id: Int,
    val title: String,
    val episodes: Int?,
    val score: Double?,
    val images: ImageWrapper
) : Parcelable

@Parcelize
data class ImageWrapper(
    val jpg: ImageDetails
) : Parcelable

@Parcelize
data class ImageDetails(
    val image_url: String
) : Parcelable

