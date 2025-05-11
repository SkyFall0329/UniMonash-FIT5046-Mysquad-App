package com.example.mysquad.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await

class AuthRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val userRepo: UserRepository = UserRepository()
) {

    val currentUser get() = auth.currentUser
    fun isSignedIn() = currentUser != null


    /** Create the Auth user and writes their username into Firestore **/
    suspend fun signUp(email: String, password: String,username: String) {
        try {
            auth.createUserWithEmailAndPassword(email, password).await()
            val user = auth.currentUser ?: throw IllegalStateException("User is null after sign-up")
            user.sendEmailVerification().await()
            println("✅ Verification email sent to: ${user.email}")
            val uid = user.uid
            userRepo.createUserProfile(uid, username, email)
            println("✅ Firestore user profile created for UID: $uid")
        } catch (e: Exception) {
            println("❌ Error during sign-up: ${e.message}")
            throw e
        }
    }

    suspend fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).await()
    }

    suspend fun resetPassword(email: String) {
        auth.sendPasswordResetEmail(email).await()
    }

    suspend fun signInWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        val result = auth.signInWithCredential(credential).await()
        // if this is their first sign-in, create a Firestore profile
        if (result.additionalUserInfo?.isNewUser == true) {
            val user = auth.currentUser!!
            val uid = user.uid
            val email = user.email ?: ""
            val username  = user.displayName ?: ""
            userRepo.createUserProfile(uid, username, email)
        }
    }

    fun signOut() = auth.signOut()
}