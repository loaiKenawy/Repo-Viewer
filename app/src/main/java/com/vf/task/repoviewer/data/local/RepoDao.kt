package com.vf.task.repoviewer.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vf.task.repoviewer.data.model.DetailedRepositoryEntity
import com.vf.task.repoviewer.data.model.RepositoryKeyEntity

@Dao
interface RepoDao {

    @Query("SELECT * FROM Repositories LIMIT :startLimit , :offset")
    suspend fun getRepoKeys(startLimit :Int, offset: Int): List<RepositoryKeyEntity>

    @Query("SELECT COUNT(id) FROM Repositories")
    suspend fun getMaxLimit():Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepoKeys(repos: List<RepositoryKeyEntity>)

    @Query("SELECT * FROM DetailedRepositories LIMIT :startLimit , :offset")
    suspend fun getDetailedRepos(startLimit :Int, offset: Int): List<DetailedRepositoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetailedRepos(repos: List<DetailedRepositoryEntity>)

}
