package com.example.aula1.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.aula1.MyApplication
import com.example.aula1.model.EventWrapper
import com.example.aula1.model.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception


class MainViewModel(private val repository: MainRepository): ViewModel() {
    val localItems = repository.getGamesFromDb()
    val requestError = MutableLiveData<EventWrapper<String>>()

    fun refreshGames() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getGamesFromApi()
                if(response.isSuccessful) {
                    // MyApplication.database!!.GameDao().deleteAll()
                    MyApplication.database!!.GameDao().insertAllGames(response.body()!!)
                } else {
                    withContext(Dispatchers.Main) {
                        requestError.value = EventWrapper("Opa! Não conseguimos atualizar os dados.")
                    }
                }
            } catch(error: Exception) {
                withContext(Dispatchers.Main) {
                    Log.d("AQUI ->", error.message?: "")
                    requestError.value = EventWrapper("Caramba! Não conseguimos atualizar os dados")
                }
            }
        }
    }

    class MainViewModelFactory(private val mainRepository: MainRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(mainRepository) as T
        }
    }
}