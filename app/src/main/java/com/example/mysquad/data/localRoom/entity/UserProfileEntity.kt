package com.example.mysquad.data.localRoom.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey val userId: String = "",
    val userName: String = "",
    val userEmail: String = "",
    val userGender: String? = null,
    val userFaculty: String? = null,
    val userDegree: String? = null,
    val userBirthday: String? = null,
    val userPreferredSports: String? = null,
    val userBio: String? = null,
    val userUpdatedAt: Long = 0L
)