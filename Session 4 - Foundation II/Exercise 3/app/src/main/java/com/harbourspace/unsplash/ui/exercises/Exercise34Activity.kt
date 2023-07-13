package com.harbourspace.unsplash.ui.exercises

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.harbourspace.unsplash.R

class Exercise34Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise34)

        findViewById<ImageView>(R.id.iv_preview).setOnClickListener { finish() }
    }
}