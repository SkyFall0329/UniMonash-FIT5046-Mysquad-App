package com.example.mysquad.data.localRoom.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mysquad.data.localRoom.entity.UserProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserProfileEntity)

    @Query("SELECT * FROM user_profile WHERE userId = :uid LIMIT 1")
    suspend fun getUserById(uid: String): UserProfileEntity?

    @Query("SELECT * FROM user_profile WHERE userId IN (:userIds)")
    suspend fun getUsersByIds(userIds: List<String>): List<UserProfileEntity>

    @Query("SELECT * FROM user_profile")
    fun getAllUsers(): Flow<List<UserProfileEntity>>

    @Delete
    suspend fun deleteUser(user: UserProfileEntity)
}