package com.nyller.android.mach3.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nyller.android.mach3.databinding.ActivityCorpoBinding

class CorpoActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCorpoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCorpoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar
        setSupportActionBar(binding.toolbarCateg.toolbar2)
        supportActionBar?.title = "Corpo & Mente"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return false
    }

}