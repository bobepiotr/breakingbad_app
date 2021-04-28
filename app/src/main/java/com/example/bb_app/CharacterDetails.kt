package com.example.bb_app

import android.app.Activity
import android.content.res.Resources
import android.graphics.Insets
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.bb_app.const.Const
import com.example.bb_app.models.Character
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import okhttp3.*
import java.io.IOException
import java.io.Serializable


class CharacterDetails : AppCompatActivity() {
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_details)
        setUp(getCharacter())
    }

    private fun getRealScreenHeight(activity: Activity): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = activity.windowManager.currentWindowMetrics
            val insets: Insets = windowMetrics.windowInsets
                    .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            windowMetrics.bounds.height() - insets.left - insets.right
        } else {
            // status bar seems to be overlooked so It needs to be included separately
            val resourceId: Int = resources.getIdentifier("status_bar_height", "dimen", "android");
            val displayMetrics = DisplayMetrics()
            activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.heightPixels - resources.getDimensionPixelSize(resourceId)
        }
    }

    private fun setUp(character: Character) {
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

        //setting top layout height
        val paramsTop: ViewGroup.LayoutParams = characterImage.layoutParams
        val padTopHeight = 0 * Resources.getSystem().displayMetrics.density.toInt()
        val realScreenHeight = getRealScreenHeight(this)
        val nameHeight = characterName.layoutParams.height
        paramsTop.height = realScreenHeight - nameHeight - padTopHeight
        characterImage.layoutParams = paramsTop
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

    fun detailsDrawCharacter(view: View) {
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
                    setUp(characters[0])
                }
            }
        })
    }
}