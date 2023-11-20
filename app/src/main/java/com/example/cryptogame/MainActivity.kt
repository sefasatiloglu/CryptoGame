package com.example.cryptogame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.cryptogame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var tasarim:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tasarim = DataBindingUtil.setContentView(this,R.layout.activity_main)

        tasarim.buttonOyna.setOnClickListener {

            val intent = Intent(this@MainActivity,OyunEkraniActivity::class.java)
            startActivity(intent)


        }

    }
}