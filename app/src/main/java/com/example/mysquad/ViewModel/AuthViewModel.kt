package com.example.mysquad.ViewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mysquad.firebase.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

sealed interface AuthUiState {
    object Idle : AuthUiState
    object Loading : AuthUiState
    object Success : AuthUiState
    data class Error(val message: String) : AuthUiState
}

class AuthViewModel(
    private val repo: AuthRepository = AuthRepository()
) : ViewModel() {

    var uiState: AuthUiState by mutableStateOf(AuthUiState.Idle)
        private set

    var tempEmail: String = ""
        private set

    fun setTempEmail(email: String) {
        tempEmail = email
    }

    fun register(email:String,pwd:String,username:String) = launch { repo.signUp(email, pwd,username) }
    fun login(email: String, pwd: String) = launch { repo.login(email, pwd) }
    fun resetPassword(email: String) = launch { repo.resetPassword(email) }
    @RequiresApi(Build.VERSION_CODES.O)
    fun signInWithGoogle(idToken: String) = launch { repo.signInWithGoogle(idToken) }
    fun signOut() {
        repo.signOut()
        uiState = AuthUiState.Idle
    }

    private fun launch(block: suspend () -> Unit) = viewModelScope.launch {
        uiState = AuthUiState.Loading
        runCatching { block() }
            .onSuccess { uiState = AuthUiState.Success }
            .onFailure { uiState = AuthUiState.Error(it.message ?: "Unknown error") }
    }
    internal fun resetUiState() {
        uiState = AuthUiState.Idle
    }

    fun getCurrentUserId(): String? {
        return FirebaseAuth.getInstance().currentUser?.uid
    }
}