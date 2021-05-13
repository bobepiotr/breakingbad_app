package com.example.bb_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun openCharacterList(view: View) {
        val intent = Intent(this, CharacterList::class.java)
        startActivity(intent)
    }

    fun openEpisodeList(view: View) {
        val intent = Intent(this, SeasonList::class.java)
        startActivity(intent)
    }

    fun openRandomQuote(view: View) {
        val intent = Intent(this, QuoteDetails::class.java)
        startActivity(intent)
    }
}