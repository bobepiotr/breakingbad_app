package com.example.bb_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bb_app.adapters.EpisodesAdapter
import com.example.bb_app.const.Const
import com.example.bb_app.models.Episode
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException
import java.util.*

class EpisodeList : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private val client: OkHttpClient = OkHttpClient()
    private var seasonNumber: Int? = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_episode_list)

        recyclerView = findViewById(R.id.ep_rec_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        seasonNumber = getSeasonNumber()
        run()
    }

    private fun run() {
        val request = Request.Builder()
            .url("https://www.breakingbadapi.com/api/episodes?series=Breaking+Bad")
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
                    val filteredEpisodes = episodes.filter{it.season.trim().toInt() == seasonNumber}
                    recyclerView.adapter = EpisodesAdapter(filteredEpisodes.toList())
                    (recyclerView.adapter as EpisodesAdapter).setOnItemClickListener(object:
                        EpisodesAdapter.ClickListener{
                        override fun onItemClick(position: Int) {
                            openDetailsActivity(filteredEpisodes[position])
                        }
                    })
                }
            }
        })
    }

    fun drawEpisode(view: View) {
        val request = Request.Builder()
                .url("https://www.breakingbadapi.com/api/episodes?series=Breaking+Bad")
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
                    val filteredEpisodes = episodes.filter {it.season.trim().toInt() == seasonNumber}
                    val randomIndex: Int = Random().nextInt(filteredEpisodes.size)
                    openDetailsActivity(filteredEpisodes[randomIndex])
                }
            }
        })
    }

    private fun openDetailsActivity(episode: Episode) {
        val intent = Intent(applicationContext, EpisodeDetails::class.java)
        val bundle = Bundle()
        bundle.putSerializable(Const.EPISODE_KEY, episode)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    private fun getSeasonNumber(): Int? {
        val bundle: Bundle? = intent.extras
        val seasonNumber: Int? = bundle?.getInt(Const.SEASON_NUMBER_KEY)
        return seasonNumber
    }
}