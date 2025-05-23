package com.example.mysquad.firebase

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class AuthRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val userRepo: UserRepository = UserRepository()
) {

    val currentUser get() = auth.currentUser
    fun isSignedIn() = currentUser != null


    /** Create the Auth user and writes their username into Firestore **/
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun signUp(email: String, password: String, username: String) {
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
        return suspendCoroutine { continuation ->
            auth.sendPasswordResetEmail(email)
                .addOnSuccessListener {
                    continuation.resume(Unit)
                }
                .addOnFailureListener { exception ->
                    val errorMsg = when (exception) {
                        is FirebaseAuthInvalidUserException -> "This email is not registered."
                        is FirebaseAuthInvalidCredentialsException -> "Invalid email format."
                        else -> exception.localizedMessage ?: "Failed to send reset email."
                    }
                    continuation.resumeWithException(Exception(errorMsg))
                }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun signInWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        val result = auth.signInWithCredential(credential).await()

        val user = auth.currentUser
        Log.d("GOOGLE_LOGIN", "Firebase Login Success: ${user?.email}")

        if (result.additionalUserInfo?.isNewUser == true && user != null) {
            Log.d("GOOGLE_LOGIN", "New use writing into Firestore")
            userRepo.createUserProfile(user.uid, user.displayName ?: "New User", user.email ?: "")
        }
    }

    fun getCurrentUserId(): String? {
        return FirebaseAuth.getInstance().currentUser?.uid
    }

    fun signOut() = auth.signOut()
}