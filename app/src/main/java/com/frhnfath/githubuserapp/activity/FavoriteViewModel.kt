package com.frhnfath.githubuserapp.activity

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.frhnfath.githubuserapp.local.Favorite
import com.frhnfath.githubuserapp.local.GithubDao
import com.frhnfath.githubuserapp.local.UserRoomDatabase

class FavoriteViewModel(application: Application) : ViewModel() {
    private val githubDao: GithubDao

    init {
        val db = UserRoomDatabase.getDatabase(application)
        githubDao = db.favoriteGithubDao()
    }

    fun getAllFavorites(): LiveData<List<Favorite>> = githubDao.getAllFavorite()
}