package com.vf.task.repoviewer.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vf.task.repoviewer.data.local.RepoDao
import com.vf.task.repoviewer.data.local.RepoEntityDB
import com.vf.task.repoviewer.data.model.DetailedRepositoryEntity
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DaoTest {
    private lateinit var database: RepoEntityDB
    private lateinit var dao: RepoDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RepoEntityDB::class.java
        ).allowMainThreadQueries().build()
        dao = database.repoDao()
    }

    @After
    fun teardown(){
        database.close()
    }

    @Test
    fun insertAndGetRepos(){
        runBlocking{
            val repos = listOf(DetailedRepositoryEntity(1, "RepoTitle" ,"Description of the repo", 123,123,123,123))
            dao.insertDetailedRepos(repos)
            val allRepos = dao.getDetailedRepos(0,1)
            assertEquals(allRepos, repos)
        }
    }
}