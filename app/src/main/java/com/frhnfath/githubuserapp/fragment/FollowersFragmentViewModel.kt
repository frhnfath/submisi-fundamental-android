package com.frhnfath.githubuserapp.fragment

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.frhnfath.githubuserapp.networking.ApiClient
import com.frhnfath.githubuserapp.response.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersFragmentViewModel : ViewModel() {
    private val _listFollowers = MutableLiveData<ArrayList<UserResponse>>()
    val listFollowers: LiveData<ArrayList<UserResponse>> = _listFollowers

    fun setFollowers(query: String) {
        ApiClient.apiInstances
            .getFollowers(query)
            .enqueue(object : Callback<ArrayList<UserResponse>> {
                override fun onResponse(
                    call: Call<ArrayList<UserResponse>>,
                    response: Response<ArrayList<UserResponse>>
                ) {
                    if (response.isSuccessful) {
                        _listFollowers.postValue(response.body())
                        Log.d(ContentValues.TAG, "onResponse: ${response.body()}")
                    }
                }

                override fun onFailure(call: Call<ArrayList<UserResponse>>, t: Throwable) {
                    Log.d("Failure :", t.message!!)
                }
            })
    }

    fun getFollowers(): LiveData<ArrayList<UserResponse>> {
        return listFollowers
    }
}