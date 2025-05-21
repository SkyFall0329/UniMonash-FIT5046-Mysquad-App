package com.example.mysquad.data.localRoom.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mysquad.data.localRoom.dao.EventDao
import com.example.mysquad.data.localRoom.dao.UserDao
import com.example.mysquad.data.localRoom.entity.UserProfileEntity
import com.example.mysquad.data.localRoom.entity.EventEntity

@Database(
    entities = [UserProfileEntity::class, EventEntity::class],
    version = 2, // ⬅️ 从 1 改为 2
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun eventDao(): EventDao
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                                context.applicationContext,
                                AppDatabase::class.java,
                                "mysquad_database"
                            ).fallbackToDestructiveMigration(false)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}