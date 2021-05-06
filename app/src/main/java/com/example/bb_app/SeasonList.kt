package com.example.bb_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bb_app.adapters.SeasonsAdapter
import com.example.bb_app.const.Const
import com.example.bb_app.models.Episode
import com.example.bb_app.models.Season
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException
import java.util.*

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

        (recyclerView.adapter as SeasonsAdapter).setOnItemClickListener(object: SeasonsAdapter.ClickListener{
            override fun onItemClick(position: Int) {
                openEpisodesList(position + 1)
            }
        })
    }

    fun drawSeason(view: View) {
        val intent: Intent = Intent(applicationContext, EpisodeList::class.java)
        val bundle: Bundle = Bundle()
        val random: Random = Random()
        val randomSeasonNumber: Int = random.nextInt(5) + 1
        bundle.putInt(Const.SEASON_NUMBER_KEY, randomSeasonNumber)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    private fun openEpisodesList(seasonNumber: Int) {
        val intent: Intent = Intent(applicationContext, EpisodeList::class.java)
        val bundle: Bundle = Bundle()
        bundle.putInt(Const.SEASON_NUMBER_KEY, seasonNumber)
        intent.putExtras(bundle)
        startActivity(intent)
    }
}