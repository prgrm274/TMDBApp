package com.programmer2704.movapp.data.local

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity (
    @PrimaryKey
    val id: Int,

    val poster_path: String?
)

data class MovieEntityResponse(
    val results: List<MovieEntity>
)