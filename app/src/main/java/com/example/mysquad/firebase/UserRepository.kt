package com.example.mysquad.firebase

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.mysquad.data.localRoom.entity.UserProfileEntity
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.time.Instant

data class UserProfileData(
    val email: String = "",
    val username: String = "",
    val createdAt: Timestamp = Timestamp.now()
)

class UserRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun createUserProfile(uid: String, username: String, email: String) {
        
        val profile = UserProfileEntity(
            userId = uid,
            userEmail = email,
            userGender = " ",
            userFaculty = " ",
            userDegree = " ",
            userBirthday = " ",
            userPreferredSports = " ",
            userBio = " ",
            userUpdatedAt = Instant.now().toEpochMilli(),
            userName = username
        )
        firestore.collection("user")
            .document(uid)
            .set(profile)
            .await()
    }
}
