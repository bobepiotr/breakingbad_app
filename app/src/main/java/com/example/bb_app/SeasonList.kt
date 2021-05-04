package com.example.bb_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bb_app.adapters.SeasonsAdapter
import com.example.bb_app.models.Episode
import com.example.bb_app.models.Season
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

class SeasonList : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private val client: OkHttpClient = OkHttpClient()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_season_list)

        recyclerView = findViewById(R.id.se_rec_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val seasonList: List<Season> = listOf(
                Season("Season 1"),
                Season("Season 2"),
                Season("Season 3"),
                Season("Season 4"),
                Season("Season 5"))
        recyclerView.adapter = SeasonsAdapter(seasonList)

        run()
    }

    private fun run() {
        val url = "https://www.breakingbadapi.com/api/episodes"
        val request = Request.Builder()
                .url(url)
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
                    (recyclerView.adapter as SeasonsAdapter).setOnItemClickListener(object: SeasonsAdapter.ClickListener{
                        override fun onItemClick(position: Int) {
                            Toast.makeText(applicationContext, "Season ${position+ 1} episodes", Toast.LENGTH_SHORT).show()
                            val filteredEpisodes = episodes.filter {
                                ((it.season.trim().toInt() == position + 1) and
                                        (it.series == "Breaking Bad"))
                            }
                            Log.d("********Season List", "Season ${position + 1}")
                            Log.d("********Season List", "Episodes amount ${filteredEpisodes.size}")
                            for (e in filteredEpisodes) {
                                Log.d("********Season List", "Episode title ${e.title}")
                            }
                        }
                    })
                }
            }
        })
    }
    fun drawEpisode(view: View) {
        Toast.makeText(applicationContext, "Random episode ...", Toast.LENGTH_SHORT).show()
    }
}