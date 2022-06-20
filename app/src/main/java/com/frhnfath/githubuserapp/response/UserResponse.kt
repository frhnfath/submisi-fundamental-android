package com.frhnfath.githubuserapp.response


data class UserResponse(

	val login: String,
	val id: Int,
	val avatar_url: String,
	val name: String? = null,
	val followers: Int? = null,
	val following: Int? = null,
	val public_repos: Int? = null,
	val company: String? = null,
	val location: String? = null
)

