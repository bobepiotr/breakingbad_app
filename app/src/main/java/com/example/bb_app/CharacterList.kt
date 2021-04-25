package com.example.bb_app

import android.content.Intent
import android.os.Bundle
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
                    openDetailsActivity(fixCharacter(characters[0]))
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

    private fun fixCharacter(ch: Character): Character {
        if (ch.birthday.isNullOrEmpty()) {
            return Character(ch.char_id, ch.name, "Unknown", ch.occupation,
                    ch.img, ch.status, ch.nickname,
                    ch.appearance, ch.portrayed, ch.category)
        }
        //"birthday":"1958-09-07T00:00:00.000Z"
        else if (ch.birthday.length > 9) {
            return Character(ch.char_id, ch.name, ch.birthday.substring(0, 10), ch.occupation,
                    ch.img, ch.status, ch.nickname,
                    ch.appearance, ch.portrayed, ch.category)
        }
        else {
            return ch
        }
    }
}
