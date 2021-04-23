package com.example.bb_app

import android.media.Image
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
        val characterNickname: TextView = findViewById(R.id.ch_nickname_desc)
        val characterPortrayed: TextView = findViewById(R.id.ch_portrayed_desc)
        val characterBorn: TextView = findViewById(R.id.ch_born_desc)
        val characterOccupation: TextView = findViewById(R.id.ch_occupation_desc)
        val characterStatus: ImageView = findViewById(R.id.ch_status_img)

        Picasso.get().load(character.img).into(characterImage)
        characterName.text = character.name
        characterNickname.text = character.nickname
        characterPortrayed.text = character.portrayed
        characterBorn.text = character.birthday
        characterOccupation.text = occupationListToString(character.occupation)
        characterStatus.setImageResource(getStatusDrawableId(character.status))
    }

    private fun getCharacter(): Character {
        val bundle: Bundle? = intent.extras
        val serializable: Serializable? = bundle?.getSerializable(Const.CHARACTER_KEY)

        return (serializable as Character)
    }

    private fun occupationListToString(list: List<String>): String {
        return list.joinToString(", ")
    }

    private fun getStatusDrawableId(status: String): Int {
        return when(status) {
            "Deceased" -> R.drawable.ch_status_dead_icon
            "Alive" -> R.drawable.ch_status_alive_icon
            "Presumed dead" -> R.drawable.ch_status_unknown_dead_icon
            else -> R.drawable.ch_status_unknown_icon
        }
    }
}