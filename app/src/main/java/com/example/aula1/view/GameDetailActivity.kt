package com.example.aula1.view

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.aula1.R
import com.example.aula1.databinding.ActivityGameDetailBinding
import com.example.aula1.databinding.ActivityMainBinding

class GameDetailActivity: AppCompatActivity() {

    private lateinit var binding: ActivityGameDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_detail)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_game_detail)

        val winnerAbbreviation = intent.getSerializableExtra("winnerAbbreviation")
        val winnerLogo = this.resources.getIdentifier(winnerAbbreviation.toString().lowercase(), "drawable", "com.example.aula1")
        val sharedPreferences = getSharedPreferences("com.example.aula1.PREFS", MODE_PRIVATE)
        val userTeam = sharedPreferences.getString("userTeam", null)

        binding.gameId.text = "Game ID: " + intent.getSerializableExtra("gameId").toString()
        binding.winner.text = "Vencedor: " + intent.getSerializableExtra("winner").toString()

        binding.winnerLogo.setImageResource(winnerLogo)

        if(winnerAbbreviation.toString() == userTeam.toString()) {
            binding.userTeam.text = "Seu time ganhou!!"
        } else {
            binding.userTeam.text = ""
        }



    }
}