package com.example.bb_app

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.bb_app.models.Character
import com.example.bb_app.models.Quote
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import okhttp3.*
import java.io.IOException


class QuoteDetails : AppCompatActivity() {
    private val client: OkHttpClient = OkHttpClient()
    lateinit var quoteText: TextView
    lateinit var authorText: TextView
    lateinit var backgroundImage: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quote_details)

        quoteText = findViewById(R.id.q_content_desc)
        authorText = findViewById(R.id.q_author_desc)
        backgroundImage = findViewById(R.id.q_background_image)


        getQuote()
    }

    private fun setUp(quote: Quote) {
        val quo: String = "\"" + quote.quote + "\""
        quoteText.text = quo
        authorText.text = quote.author
    }

    private fun setUpBackground(img: String) {
        Picasso.get().load(img).into(backgroundImage)
    }

    private fun getQuote() {
        val request = Request.Builder()
                .url("https://www.breakingbadapi.com/api/quote/random")
                .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                val gson = GsonBuilder().create()
                val quote: Array<Quote> = gson.fromJson(
                        body,
                        Array<Quote>::class.java
                )
                if (!quote.isNullOrEmpty()) {
                    quote[0].fixQuoteAuthor()
                    runOnUiThread {
                        setUp(quote[0])
                    }
                    getCharacterAndSetupBackground(quote[0].author)
                }
            }
        })
    }

    private fun getCharacterAndSetupBackground(name: String) {
        val reqName = name.replace(" ", "+")
        val request = Request.Builder()
                .url("https://www.breakingbadapi.com/api/characters?name=${reqName}")
                .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                val gson = GsonBuilder().create()
                val character: Array<Character> = gson.fromJson(
                        body,
                        Array<Character>::class.java
                )
                if (!character.isNullOrEmpty()) {
                    runOnUiThread {
                        setUpBackground(character[0].img)
                    }
                }
            }
        })
    }

    fun drawQuote(view: View) {
        getQuote()
    }
}