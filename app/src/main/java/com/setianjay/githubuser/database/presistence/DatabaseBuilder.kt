package com.setianjay.githubuser.database.presistence

import android.content.Context
import androidx.room.Room
import com.setianjay.githubuser.utill.Constant

object DatabaseBuilder {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                Constant.DATABASE.DB_NAME
            ).build()
            INSTANCE = instance
            instance
        }
    }
}