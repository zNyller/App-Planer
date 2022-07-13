package com.nyller.android.mach3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.nyller.android.mach3.activities.HabitosActivity
import com.nyller.android.mach3.databinding.FragmentHomeBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onStart() {
        super.onStart()

        binding.imageButton.setOnClickListener {
            startActivity(Intent(this, HabitosActivity::class.java))
        }

    }

}