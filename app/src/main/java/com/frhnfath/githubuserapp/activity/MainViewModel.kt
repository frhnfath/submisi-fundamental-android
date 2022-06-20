package com.frhnfath.githubuserapp.activity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.frhnfath.githubuserapp.response.SearchResponse
import com.frhnfath.githubuserapp.response.UserResponse
import com.frhnfath.githubuserapp.networking.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val listUser = MutableLiveData<ArrayList<UserResponse>>()

    fun searchUser(query: String) {
        ApiClient.apiInstances
            .searchUsers(query)
            .enqueue(object : Callback<SearchResponse> {
                override fun onResponse(
                    call: Call<SearchResponse>,
                    response: Response<SearchResponse>
                ) {
                    if (response.isSuccessful) {
                        listUser.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    Log.d("Failure :", t.message!!)
                }
            })
    }

    fun getSearch(): LiveData<ArrayList<UserResponse>> {
        return listUser
    }
}