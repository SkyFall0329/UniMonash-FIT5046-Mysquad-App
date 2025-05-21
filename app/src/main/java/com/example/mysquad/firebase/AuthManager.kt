package com.example.mysquad.firebase

import com.google.firebase.auth.FirebaseAuth

object AuthManager {
    fun getCurrentUserId(): String? {
        return FirebaseAuth.getInstance().currentUser?.uid
    }

    fun isLoggedIn(): Boolean {
        return getCurrentUserId() != null
    }
}