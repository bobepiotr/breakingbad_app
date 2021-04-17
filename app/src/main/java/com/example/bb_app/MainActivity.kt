package com.example.bb_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private var counter: Int = 0
    private var textView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.textView = findViewById(R.id.counter_text)
    }

    fun incrementCounter(view: View) {
        this.counter++

        this.textView?.text =
            if (this.counter != 1) "Button clicked $counter times"
            else "Button clicked $counter time"
    }
}