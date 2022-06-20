package com.frhnfath.githubuserapp.networking

import com.frhnfath.githubuserapp.response.SearchResponse
import com.frhnfath.githubuserapp.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

private const val auth = "Authorization: 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"

interface ApiService {
    @GET("search/users")
    @Headers(auth)
    fun searchUsers(@Query("q") query: String): Call<SearchResponse>

    @GET("users/{username}")
    @Headers(auth)
    fun getUser(@Path("username") username: String): Call<UserResponse>

    @GET("users/{username}/followers")
    @Headers(auth)
    fun getFollowers(@Path("username") username: String): Call<ArrayList<UserResponse>>

    @GET("users/{username}/following")
    @Headers(auth)
    fun getFollowing(@Path("username") username: String): Call<ArrayList<UserResponse>>
}