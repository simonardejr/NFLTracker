package com.example.aula1.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.aula1.R
import com.example.aula1.model.SplashRepository
import com.example.aula1.viewmodel.SplashViewModel

class SplashActivity: AppCompatActivity() {

    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        viewModel = ViewModelProvider(this, SplashViewModel.SplashViewModelFactory(SplashRepository()))
            .get(SplashViewModel::class.java)

        viewModel.authenticatedUserLiveData.observe(this) { user ->
            if(user != null) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra(MainActivity.USER_EXTRA, user)

                // SharedPreferences
                val sharedPreferences = getSharedPreferences("com.example.aula1.PREFS", MODE_PRIVATE)
                with(sharedPreferences.edit()) {
                    putString("userTeamLabel", "Torcedor do Seahawks")
                    putString("userTeam", "SEA")
                    commit()
                }

                startActivity(intent)
                finish()
            } else {
                startActivity(Intent(this, AuthActivity::class.java))
                finish()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.checkIfUserIsAuthenticated()
    }
}