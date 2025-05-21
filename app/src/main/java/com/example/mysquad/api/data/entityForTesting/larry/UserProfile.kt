package com.example.mysquad.api.data.entityForTesting.larry

data class UserProfile(
    val userID: String,
    val userName: String,
    val userEmail: String,
    val faculty: String,
    val degree: String, // "undergraduate" | "master" | "phd"
    val birthday: String,
    val favoriteSports: String,
    val bio: String,
    val gender: String // "male", "female", "other"
)