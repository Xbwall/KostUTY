package com.example.kostuty.User

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kostuty.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener({
            startActivity(Intent(this, WelcomeActivity::class.java))
        })

        binding.btnRegister.setOnClickListener({
            startActivity(Intent(this, RegisterActivity::class.java))
        })

    }
}