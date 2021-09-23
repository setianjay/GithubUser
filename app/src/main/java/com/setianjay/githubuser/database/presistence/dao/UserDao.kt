package com.setianjay.githubuser.database.presistence.dao

import androidx.room.*
import com.setianjay.githubuser.database.presistence.entity.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM tb_user ORDER BY username ASC")
    fun getUsers(): Flow<List<User>>

    @Query("SELECT * FROM tb_user WHERE username = :username")
    fun getSpecificUser(username: String): Flow<User>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavorite(user: User)

    @Delete
    suspend fun deleteFavorite(user: User)
}