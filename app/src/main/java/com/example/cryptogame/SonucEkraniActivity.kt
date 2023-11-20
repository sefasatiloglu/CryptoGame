package com.example.cryptogame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.cryptogame.databinding.ActivitySonucEkraniBinding

class SonucEkraniActivity : AppCompatActivity() {
    private lateinit var tasarim:ActivitySonucEkraniBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tasarim=DataBindingUtil.setContentView(this,R.layout.activity_sonuc_ekrani)

        val skor = intent.getIntExtra("skor",0)
        tasarim.textViewTotalScore.text = skor.toString()

        tasarim.buttonAgain.setOnClickListener {

            val intent = Intent(this@SonucEkraniActivity,MainActivity::class.java)
            startActivity(intent)


        }

    }
}