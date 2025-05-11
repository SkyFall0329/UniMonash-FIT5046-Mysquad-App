package com.example.mysquad.firebase

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

data class UserProfileData(
    val email: String = "",
    val username: String = "",
    val createdAt: Timestamp = Timestamp.now()
)

class UserRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    suspend fun createUserProfile(uid: String, username: String, email: String) {
        val profile = UserProfileData(email = email, username = username)
        firestore.collection("user")
            .document(uid)
            .set(profile)
            .await()
    }
}
