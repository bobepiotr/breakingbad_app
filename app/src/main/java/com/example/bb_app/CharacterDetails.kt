package com.example.bb_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.bb_app.models.Character
import com.example.bb_app.const.Const
import com.squareup.picasso.Picasso
import java.io.Serializable

class CharacterDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_details)
        setUp()
    }

    private fun setUp() {
        val character: Character = getCharacter()

        val characterImage: ImageView = findViewById(R.id.ch_photo_img)
        val characterName: TextView = findViewById(R.id.ch_name_desc)

        Picasso.get().load(character.img).into(characterImage)
        characterName.text = character.name
    }

    private fun getCharacter(): Character {
        val bundle: Bundle? = intent.extras
        val serializable: Serializable? = bundle?.getSerializable(Const.CHARACTER_KEY)

        return (serializable as Character)
    }
}