package com.example.mysquad.data.repository

import com.example.mysquad.data.localRoom.dao.UserDao
import com.example.mysquad.data.localRoom.entity.UserProfileEntity
import com.example.mysquad.data.remoteFireStore.UserRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepository(
    private val userDao: UserDao,
    internal val remote: UserRemoteDataSource
) {

    suspend fun createOrUpdateUser(user: UserProfileEntity) {
        userDao.insertUser(user)
        remote.uploadUser(user)
    }

    suspend fun syncUsersToLocal() {
        val remoteUsers = remote.fetchAllUsers()
        remoteUsers.forEach { userDao.insertUser(it) }
    }

    fun getLocalUser(uid: String): Flow<UserProfileEntity?> = flow {
        emit(userDao.getUserById(uid))
    }

    fun getAllLocalUsers(): Flow<List<UserProfileEntity>> = userDao.getAllUsers()
}