package com.example.mysquad.data.localRoom.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey val userId: String = "",
    val userName: String = "",
    val userEmail: String = "",
    val userGender: String = "",
    val userFaculty: String = "",
    val userDegree: String = "",
    val userBirthday: String = "",
    val userPreferredSports: String = "",
    val userBio: String = "",
    val userUpdatedAt: Long = 0L
)