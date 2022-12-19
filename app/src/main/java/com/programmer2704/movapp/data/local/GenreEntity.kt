package com.programmer2704.movapp.data.local

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genres")
data class GenreEntity (
    @PrimaryKey
    val id: Int,

    @NonNull
    val name: String
)

data class GenreEntityResponse(
    val results: List<GenreEntity>
)