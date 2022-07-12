package com.nyller.android.mach3.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import com.google.android.material.button.MaterialButton
import com.nyller.android.mach3.R
import com.nyller.android.mach3.databinding.ActivityHabitosBinding
import com.nyller.android.mach3.databinding.ActivityMainBinding
import com.nyller.android.mach3.databinding.ToolbarBinding

class HabitosActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHabitosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHabitosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar
        setSupportActionBar(binding.include2.toolbar)
        supportActionBar?.title = "Criar hábito"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onStart() {
        super.onStart()

        binding.buttonNovoHabito.setOnClickListener {
            startActivity(Intent(this, NovoHabitoActivity::class.java))
        }

        binding.buttonCorpoMente.setOnClickListener {
            startActivity(Intent(this, CorpoActivity::class.java))
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return false
    }

}
