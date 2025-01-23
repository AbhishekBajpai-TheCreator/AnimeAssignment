package com.example.animeapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var animeAdapter: AnimeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerViewAnime)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchTopAnime()
    }

    private fun fetchTopAnime() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitClient.apiService.getTopAnime()
                if (response.isSuccessful) {
                    val animeResponse = response.body()
                    animeResponse?.let {
                        withContext(Dispatchers.Main) {
                            animeAdapter = AnimeAdapter(it.data) { anime ->
                                val intent = Intent(this@MainActivity, AnimeDetailActivity::class.java)
                                intent.putExtra("mal_id", anime.mal_id)
                                startActivity(intent)
                            }
                            recyclerView.adapter = animeAdapter
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@MainActivity, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "An error occurred: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}


