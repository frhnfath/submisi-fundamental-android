package com.frhnfath.githubuserapp.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface GithubDao {

    @Query("SELECT * FROM favorites ")
    fun getAllFavorite(): LiveData<List<Favorite>>

    @Query("SELECT count(*) FROM favorites WHERE favorites.login = :username")
    suspend fun checkFavorite(username: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favorite: Favorite)

    @Query("DELETE FROM favorites WHERE favorites.login = :username")
    suspend fun deleteFavorite(username: String): Int

}