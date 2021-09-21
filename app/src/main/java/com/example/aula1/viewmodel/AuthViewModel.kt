package com.example.aula1.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.aula1.model.AuthRepository
import com.example.aula1.model.EventWrapper
import com.example.aula1.model.User
import com.google.firebase.auth.AuthCredential

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {
    enum class SignInType(val type: String) {
        Google("Google"),
    }

    val authButtonLiveData = MutableLiveData<EventWrapper<SignInType>>()
    val authenticatedUserLiveData = MutableLiveData<User>()
    val createdUserLiveData = MutableLiveData<User>()

    fun handleGoogleSignInClick() {
        authButtonLiveData.value = EventWrapper(SignInType.Google)
    }

    fun signInWithGoogle(googleAuthCredential: AuthCredential) {
        authRepository.firebaseSignInWithGoogle(googleAuthCredential, authenticatedUserLiveData)
    }

    fun createUser(user: User) {
        authRepository.createUserInFirestoreIfNotExists(user, createdUserLiveData)
    }

    class AuthViewModelFactory(private val authRepository: AuthRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return AuthViewModel(authRepository) as T
        }
    }
}