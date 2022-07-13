package com.nyller.android.mach3.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nyller.android.mach3.databinding.ActivityCorpoBinding
import com.nyller.android.mach3.databinding.ActivityFocoBinding

class FocoActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFocoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFocoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar
        setSupportActionBar(binding.toolbarCateg.toolbar2)
        supportActionBar?.title = "Foco & Mindset"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return false
    }

}