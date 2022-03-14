package com.cherish.randomfactproject.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cherish.randomfactproject.R
import com.cherish.randomfactproject.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var  binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}