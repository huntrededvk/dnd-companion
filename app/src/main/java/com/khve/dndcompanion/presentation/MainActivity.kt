package com.khve.dndcompanion.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.khve.dndcompanion.R
import com.khve.dndcompanion.databinding.ActivityMainBinding
import com.khve.dndcompanion.presentation.auth.AuthFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction()
            .replace(R.id.auth_container, AuthFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

}