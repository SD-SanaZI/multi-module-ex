package com.sanazi.list.domain

interface UserRepository{
    suspend fun update(): List<Course>
    suspend fun setFavorite(id: Int, hasLike: Boolean)
}