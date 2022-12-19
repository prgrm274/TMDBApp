package com.programmer2704.movapp.data.local

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.programmer2704.movapp.model.VideoData

data class Video (
    val id: Int,
)

data class VideoResponse(
    val results: List<VideoData>
)