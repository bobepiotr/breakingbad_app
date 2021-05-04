package com.example.bb_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bb_app.adapters.CharactersAdapter
import com.example.bb_app.adapters.EpisodesAdapter
import com.example.bb_app.models.Character
import com.example.bb_app.models.Episode
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

class EpisodeList : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private val client: OkHttpClient = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_episode_list)

        recyclerView = findViewById(R.id.ep_rec_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun run() {
        val request = Request.Builder()
            .url("https://www.breakingbadapi.com/api/episodes")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                val gson = GsonBuilder().create()
                val episodes: Array<Episode> = gson.fromJson(
                    body,
                    Array<Episode>::class.java
                )

                runOnUiThread {
                    recyclerView.adapter = EpisodesAdapter(episodes.toList())
                    (recyclerView.adapter as EpisodesAdapter).setOnItemClickListener(object:
                        EpisodesAdapter.ClickListener{
                        override fun onItemClick(position: Int) {
                            openDetailsActivity(episodes[position])
                        }
                    })
                }
            }
        })
    }

    fun drawEpisode(view: View) {
        Toast.makeText(applicationContext, "Random episode", Toast.LENGTH_SHORT).show()
    }

    fun openDetailsActivity(episode: Episode) {

    }
}