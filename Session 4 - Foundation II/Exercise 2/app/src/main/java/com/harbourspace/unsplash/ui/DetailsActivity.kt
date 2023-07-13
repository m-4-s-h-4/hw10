package com.harbourspace.unsplash.ui

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.harbourspace.unsplash.R

private const val TAG = "DetailsActivity"

class DetailsActivity : AppCompatActivity() {

    private lateinit var viewModel : EasterEggViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        Toast.makeText(applicationContext, "onCreate", Toast.LENGTH_SHORT).show()

        viewModel = ViewModelProvider(this)[EasterEggViewModel::class.java]
        viewModel.number.observe(this) {
            if (it > 2) {
                Toast.makeText(applicationContext, "Easter egg found 🐣", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        viewModel.increment()
    }

    override fun onResume() {
        super.onResume()

        Toast.makeText(applicationContext, "onResume", Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        Toast.makeText(applicationContext, "onPause", Toast.LENGTH_SHORT).show()

        super.onPause()
    }

    override fun onDestroy() {
        Toast.makeText(applicationContext, "onDestroy", Toast.LENGTH_SHORT).show()

        super.onDestroy()
    }
}