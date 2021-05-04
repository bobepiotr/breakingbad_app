package com.example.bb_app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bb_app.R
import com.example.bb_app.models.Episode

class EpisodesAdapter(
    private val episodes: List<Episode>
                        ): RecyclerView.Adapter<EpisodesAdapter.CustomViewHolder>() {
    private lateinit var clickListener: ClickListener

    override fun getItemCount(): Int {
        return episodes.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemLayout = layoutInflater.inflate(R.layout.episode_item, parent, false)

        return CustomViewHolder(itemLayout)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val episode: Episode = episodes[position]

        holder.titleText.text = episode.title
        holder.episodeText.text = "   Episode "+episode.episode
        holder.view.setOnClickListener(View.OnClickListener {
            clickListener.onItemClick(position)
        })
    }

    fun setOnItemClickListener(listener: ClickListener) {
        this.clickListener = listener
    }

    class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val titleText: TextView
        val episodeText: TextView
        val avatar: ImageView

        init {
            titleText = view.findViewById(R.id.ep_title_text)
            episodeText = view.findViewById(R.id.ep_episode_text)
            avatar = view.findViewById(R.id.episode_avatar)
        }
    }

    interface ClickListener {
        fun onItemClick(position: Int)
        //fun onLongItemClick(position: Int, view: View?)
    }
}