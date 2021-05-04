package com.example.bb_app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bb_app.adapters.CharactersAdapter
import com.example.bb_app.const.Const
import com.example.bb_app.models.Character
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

class CharacterList : AppCompatActivity() {
    private val client = OkHttpClient()
    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_list)

        recyclerView = findViewById(R.id.recViewChars)
        recyclerView?.layoutManager = LinearLayoutManager(this)
        run("https://www.breakingbadapi.com/api/characters")
    }

    private fun run(url: String) {
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                val gson = GsonBuilder().create()
                val characters: Array<Character> = gson.fromJson(
                    body,
                    Array<Character>::class.java
                )

                runOnUiThread {
                    recyclerView?.adapter = CharactersAdapter(characters.toList())
                    (recyclerView?.adapter as CharactersAdapter).setOnItemClickListener(object:CharactersAdapter.ClickListener{
                        override fun onItemClick(position: Int) {
                            openDetailsActivity(characters[position])
                        }
                    })
                }
            }
        })
    }

    fun drawCharacter(view: View) {
        val request = Request.Builder()
                .url("https://www.breakingbadapi.com/api/character/random")
                .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                val gson = GsonBuilder().create()
                val characters: Array<Character> = gson.fromJson(
                        body,
                        Array<Character>::class.java
                )
                runOnUiThread {
                    characters[0].fixCharacter()
                    openDetailsActivity(characters[0])
                }
            }
        })
    }

    private fun openDetailsActivity(character: Character) {
        val intent: Intent = Intent(applicationContext, CharacterDetails::class.java)
        val bundle: Bundle = Bundle()
        bundle.putSerializable(Const.CHARACTER_KEY, character)
        intent.putExtras(bundle)
        startActivity(intent)
    }
}
