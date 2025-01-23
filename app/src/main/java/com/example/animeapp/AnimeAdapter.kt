package com.example.animeapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class AnimeAdapter(private val animeList: List<Anime>, private val onItemClick: (Anime) -> Unit) :
    RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder>() {

    inner class AnimeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagePoster: ImageView = itemView.findViewById(R.id.imageAnimePoster)
        val textTitle: TextView = itemView.findViewById(R.id.textAnimeTitle)
        val textEpisodes: TextView = itemView.findViewById(R.id.textAnimeEpisodes)
        val textRating: TextView = itemView.findViewById(R.id.textAnimeRating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_anime, parent, false)
        return AnimeViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        val anime = animeList[position]

        holder.textTitle.text = anime.title
        holder.textEpisodes.text = "Episodes: ${anime.episodes ?: "N/A"}"
        holder.textRating.text = "Rating: ${anime.score ?: "N/A"}"

        Glide.with(holder.itemView.context)
            .load(anime.images.jpg.image_url)
            .into(holder.imagePoster)

        holder.itemView.setOnClickListener {
            onItemClick(anime)
        }
    }

    override fun getItemCount(): Int = animeList.size
}
