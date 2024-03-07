package com.vf.task.repoviewer.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vf.task.repoviewer.data.model.DetailedRepositoryEntity
import com.vf.task.repoviewer.data.model.RepositoryKeyEntity

@Database(entities = [RepositoryKeyEntity::class , DetailedRepositoryEntity::class], version = 1)
abstract class RepoEntityDB :RoomDatabase() {

    abstract fun repoDao(): RepoDao

    companion object {
        @Volatile
        private var INSTANCE: RepoEntityDB? = null

        fun getInstance(context: Context): RepoEntityDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RepoEntityDB::class.java,
                    "RepositoriesDatabase"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
