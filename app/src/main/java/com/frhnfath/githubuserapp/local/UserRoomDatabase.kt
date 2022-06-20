package com.frhnfath.githubuserapp.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Favorite::class], version = 1)
abstract class UserRoomDatabase : RoomDatabase() {
    abstract fun favoriteGithubDao(): GithubDao

    companion object {
        @Volatile
        private var INSTANCE: UserRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): UserRoomDatabase {
            if (INSTANCE == null) {
                synchronized(UserRoomDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        UserRoomDatabase::class.java, "favorite_database")
                        .build()
                }
            }
            return INSTANCE as UserRoomDatabase
        }
    }
}