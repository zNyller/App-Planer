package com.nyller.android.mach3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nyller.android.mach3.databinding.FragmentHomeBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}