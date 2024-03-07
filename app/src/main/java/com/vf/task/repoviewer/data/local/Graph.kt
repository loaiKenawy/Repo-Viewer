package com.vf.task.repoviewer.data.local

import android.content.Context
import com.vf.task.repoviewer.data.repository.ReposRepository

object Graph {
    private lateinit var repoDatabase: RepoEntityDB

    val repository by lazy {
        ReposRepository(repoDatabase.repoDao())
    }

    fun provide(context: Context) {
        repoDatabase = RepoEntityDB.getInstance(context)
    }

}