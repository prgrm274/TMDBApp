package com.programmer2704.movapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.programmer2704.movapp.data.local.remotekeys.RemoteKeys
import com.programmer2704.movapp.data.local.remotekeys.RemoteKeysDao

@Database(
    entities = [
        MovieEntity::class,
        GenreEntity::class,

        TopratedEntity::class,
        RemoteKeys::class,
    ],
    version = 2,
    exportSchema = false)
abstract class MyDB : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun genreDao(): GenreDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {
        @Volatile
        private var INSTANCE: MyDB? = null

        fun getDatabase(context: Context): MyDB {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyDB::class.java,
                    "app_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}