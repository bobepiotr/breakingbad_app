package com.example.bb_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.example.bb_app.const.Const
import com.example.bb_app.models.Character
import com.example.bb_app.models.Episode
import com.google.gson.GsonBuilder
import okhttp3.*
import org.w3c.dom.Text
import java.io.IOException
import java.io.Serializable
import java.util.*

class EpisodeDetails : AppCompatActivity() {
    private lateinit var episode: Episode
    private lateinit var episodeName: TextView
    private lateinit var episodeNumber:TextView
    private lateinit var premiereDate: TextView
    private lateinit var starring: TextView

    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_episode_details)

        episode = getEpisode()
        episodeName = findViewById(R.id.ep_name_desc)
        episodeNumber = findViewById(R.id.ep_episode_num_desc)
        premiereDate = findViewById(R.id.ep_premiere_desc)
        starring = findViewById(R.id.ep_starring_desc)

        setUp(episode)
    }

    fun detailsDrawEpisode(view: View) {
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
                    val filteredEpisodes = episodes.filter {it.season.trim() == episode.season}
                    val randomIndex: Int = Random().nextInt(filteredEpisodes.size)
                    setUp(filteredEpisodes[randomIndex])
                }
            }
        })
    }

    private fun getEpisode(): Episode {
        val bundle: Bundle? = intent.extras
        val serializable: Serializable? = bundle?.getSerializable(Const.EPISODE_KEY)

        return (serializable as Episode)
    }

    private fun setUp(episode: Episode) {
        episodeName.text = episode.title
        val ep = "Episode "+episode.episode
        episodeNumber.text = ep
        premiereDate.text = episode.air_date
        starring.text = episode.characters.joinToString("\n")
    }
}