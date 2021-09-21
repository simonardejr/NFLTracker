package com.example.aula1.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aula1.R
import com.example.aula1.databinding.ActivityMainBinding
import com.example.aula1.model.MainRepository
import com.example.aula1.model.User
import com.example.aula1.view.adapter.GameAdapter
import com.example.aula1.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    companion object {
        val USER_EXTRA = "user"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private val gameAdapter = GameAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.user = intent.getSerializableExtra(USER_EXTRA) as User

        val sharedPreferences = getSharedPreferences("com.example.aula1.PREFS", MODE_PRIVATE)
        val userTeam = sharedPreferences.getString("userTeamLabel", null)
        binding.userTeam.text = userTeam

        viewModel = ViewModelProvider(this, MainViewModel.MainViewModelFactory(MainRepository()))
            .get(MainViewModel::class.java)

        binding.gameList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = gameAdapter
        }

        viewModel.refreshGames()

        viewModel.localItems.observe(this) { games ->
            games?.let {
                gameAdapter.update(it)
            }
        }

        viewModel.requestError.observe(this) { wrapper ->
            wrapper.getContentIfNotHandled()?.let { errorMessage ->
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }

        }
    }

//    override fun onResume() {
//        super.onResume();
//        Toast.makeText(this, "entrou no onResume", Toast.LENGTH_SHORT).show()
//    }
}