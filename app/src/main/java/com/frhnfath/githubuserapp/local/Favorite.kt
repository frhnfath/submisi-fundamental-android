package com.frhnfath.githubuserapp.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favorites")
data class Favorite (
    @PrimaryKey
    val id: Int,
    val login: String,
    val avatar_url: String
): Serializable