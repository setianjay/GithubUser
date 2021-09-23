package com.setianjay.githubuser.database.presistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.setianjay.githubuser.database.presistence.dao.UserDao
import com.setianjay.githubuser.database.presistence.entity.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao
}