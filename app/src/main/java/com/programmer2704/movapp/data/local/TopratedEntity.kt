package com.programmer2704.movapp.data.local

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "topratedmovies")
data class TopratedEntity (
    @PrimaryKey
    val id: Int,
    @NonNull
    val poster_path: String?
)

data class TopratedEntityResponse(val results: List<TopratedEntity>)