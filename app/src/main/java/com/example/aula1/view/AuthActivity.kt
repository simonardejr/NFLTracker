package com.example.aula1.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.aula1.R
import com.example.aula1.databinding.ActivityAuthBinding
import com.example.aula1.model.AuthRepository
import com.example.aula1.model.User
import com.example.aula1.viewmodel.AuthViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.GoogleAuthProvider

class AuthActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private lateinit var authViewModel: AuthViewModel

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var signInResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_auth)
        authViewModel = ViewModelProvider(this, AuthViewModel.AuthViewModelFactory(AuthRepository())).get(AuthViewModel::class.java)

        initRegistersAndObservers()
    }

    private fun initRegistersAndObservers() {
        binding.googleSignInButton.setOnClickListener {
            authViewModel.handleGoogleSignInClick()
        }

        authViewModel.authButtonLiveData.observe(this) { signInType ->
            signInType.getContentIfNotHandled()?.let { type ->
                when(type) {
                    AuthViewModel.SignInType.Google -> signInWithGoogle()
                    else -> Log.e("Sign in Type", "Not found")
                }
            }
        }

        authViewModel.authenticatedUserLiveData.observe(this) { user ->
            // Log.d("user", user.email?: "Sem email")
            if(user.isNew) {
                authViewModel.createUser(user)
            } else {
                goToMainActivity(user)
            }
        }

        authViewModel.createdUserLiveData.observe(this) { user ->
            goToMainActivity(user)
        }

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.oauth_token))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        signInResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if(result.resultCode == RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                val googleAccount = task.result
                if(googleAccount != null) {
                    getGoogleAuthCredential(googleAccount)
                }
            }

        }
    }

    private fun goToMainActivity(user: User) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(MainActivity.USER_EXTRA, user)
        startActivity(intent)
    }

    private fun getGoogleAuthCredential(googleAccount: GoogleSignInAccount) {
        val googleTokenId = googleAccount.idToken
        val googleAuthCredential = GoogleAuthProvider.getCredential(googleTokenId, null)
        authViewModel.signInWithGoogle(googleAuthCredential)

    }

    private fun signInWithGoogle() {
        signInResultLauncher.launch(googleSignInClient.signInIntent)
    }
}