package com.example.animeapp

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AnimeDetailActivity : AppCompatActivity() {

    private lateinit var titleTextView: TextView
    private lateinit var synopsisTextView: TextView
    private lateinit var genresTextView: TextView
    private lateinit var castTextView: TextView
    private lateinit var episodesTextView: TextView
    private lateinit var ratingTextView: TextView
    private lateinit var posterImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anime_detail)

        titleTextView = findViewById(R.id.titleTextView)
        synopsisTextView = findViewById(R.id.synopsisTextView)
        genresTextView = findViewById(R.id.genresTextView)
        castTextView = findViewById(R.id.castTextView)
        episodesTextView = findViewById(R.id.episodesTextView)
        ratingTextView = findViewById(R.id.ratingTextView)
        posterImageView = findViewById(R.id.posterImageView)

        val malId = intent.getIntExtra("mal_id", -1)
        if (malId != -1) {
            fetchAnimeDetails(malId)
        } else {
            Toast.makeText(this, "Error: Invalid anime ID", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun fetchAnimeDetails(malId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitClient.apiService.getAnimeDetails(malId)
                if (response.isSuccessful) {
                    val animeDetailsResponse = response.body()
                    animeDetailsResponse?.data?.let { animeDetails ->
                        withContext(Dispatchers.Main) {
                            updateUI(animeDetails)
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@AnimeDetailActivity, "Error: ${response.code()}", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("Imageerror","Error occured:${e.message}")
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@AnimeDetailActivity, "An error occurred: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun updateUI(animeDetails: AnimeDetails) {
        titleTextView.text = animeDetails.title
        synopsisTextView.text = animeDetails.synopsis
        genresTextView.text = "Genres: ${
            animeDetails.genres?.joinToString(", ") { it.name } ?: "N/A"
        }"
        castTextView.text = "Main Cast: ${
            animeDetails.characters?.joinToString(", ") { it.name } ?: "N/A"
        }"
        episodesTextView.text = "Episodes: ${animeDetails.episodes ?: "N/A"}"
        ratingTextView.text = "Rating: ${animeDetails.score ?: "N/A"}"

        Glide.with(this)
            .load(animeDetails.images.jpg.large_image_url)
            .into(posterImageView)
    }


}



