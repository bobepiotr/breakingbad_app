package com.example.bb_app

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bb_app.adapters.CharactersAdapter
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
                            Toast.makeText(applicationContext, "Temporary message, item $position", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }
        })
    }

    fun drawCharacter(view: View) {
        Toast.makeText(applicationContext, "Tmp... random character...", Toast.LENGTH_SHORT).show()
    }


}
