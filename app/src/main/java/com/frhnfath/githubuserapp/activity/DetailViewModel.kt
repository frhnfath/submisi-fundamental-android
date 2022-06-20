package com.frhnfath.githubuserapp.activity

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.frhnfath.githubuserapp.local.Favorite
import com.frhnfath.githubuserapp.local.GithubDao
import com.frhnfath.githubuserapp.local.UserRoomDatabase
import com.frhnfath.githubuserapp.response.UserResponse
import com.frhnfath.githubuserapp.networking.ApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    val user = MutableLiveData<UserResponse>()
    private var githubDao: GithubDao

    init {
        val db = UserRoomDatabase.getDatabase(application)
        githubDao = db.favoriteGithubDao()
    }

    fun addFavorite(username: String, id: Int, avatar: String) {
        CoroutineScope(Dispatchers.IO).launch {
            var user = Favorite(
                id = id,
                login = username,
                avatar_url = avatar
            )
            Log.d("changed", "addFavorite: ")
            githubDao.addFavorite(user)
        }
    }

    suspend fun checkFavorite(username: String) = githubDao.checkFavorite(username)

    fun deleteFavorite(username: String) {
        CoroutineScope(Dispatchers.IO).launch {
            githubDao.deleteFavorite(username)
        }
    }

    fun setUser(username: String) {
        ApiClient.apiInstances
            .getUser(username)
            .enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful)  {
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.d("Fail ", t.message!!)
                }
            })
    }
}