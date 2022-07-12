package com.nyller.android.mach3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.nyller.android.mach3.databinding.FragmentHomeBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(
            binding.navHostFragment.id) as NavHostFragment
        val navController = navHostFragment.navController

    }

    override fun onStart() {
        super.onStart()


    }

}