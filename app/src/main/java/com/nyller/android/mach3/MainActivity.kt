package com.nyller.android.mach3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.nyller.android.mach3.activities.HabitosActivity
import com.nyller.android.mach3.databinding.ActivityMainBinding
import com.nyller.android.mach3.databinding.ActivityNovoHabitoBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        binding.btnAddHabito.setOnClickListener {
            startActivity(Intent(this, HabitosActivity::class.java))
        }

    }

}