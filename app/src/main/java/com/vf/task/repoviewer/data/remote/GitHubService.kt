package com.vf.task.repoviewer.data.remote

import com.vf.task.repoviewer.data.model.DetailedRepositoryEntity
import com.vf.task.repoviewer.data.model.IssueEntity
import com.vf.task.repoviewer.data.model.RepositoryKeyEntity
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubService {

    @GET("repositories")
    suspend fun getKeys(): List<RepositoryKeyEntity>

    @GET("repos/{owner}/{repo}")
    suspend fun getDetailedRepository(
        @Path("owner") ownerName: String,
        @Path("repo") repoName: String
    ): DetailedRepositoryEntity

    @GET("repos/{owner}/{repo}/issues")
    suspend fun getIssues(
        @Path("owner") ownerName: String,
        @Path("repo") repoName: String
    ): List<IssueEntity>


}