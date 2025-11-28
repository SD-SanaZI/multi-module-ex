package com.sanazi.list.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LikeState(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "first_name") val hasLike: Boolean,
)