package com.vf.task.repoviewer.data.repository

import android.util.Log
import com.vf.task.repoviewer.data.local.RepoDao
import com.vf.task.repoviewer.data.model.DetailedRepositoryEntity
import com.vf.task.repoviewer.data.model.IssueEntity
import com.vf.task.repoviewer.data.model.RepositoryKeyEntity
import com.vf.task.repoviewer.data.remote.GitHubService
import com.vf.task.repoviewer.data.remote.RetrofitClient
import com.vf.task.repoviewer.util.NetworkStateHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class
ReposRepository(private val dao: RepoDao) {

    private val TAG = "Repo"
    private var service: GitHubService? = null

    init {
        service = RetrofitClient.getInstance()
    }

    private suspend fun getAndCacheKeys() {
        val keys: List<RepositoryKeyEntity>?
        try {
            withContext(Dispatchers.IO) {
                keys = service!!.getKeys()
                dao.insertRepoKeys(keys)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Connection Error: ${e.message}")
        }
    }

    private suspend fun getAndCacheRepos(
        startLimit: Int,
        offset: Int
    ): List<DetailedRepositoryEntity> {

        val detailedReposList = ArrayList<DetailedRepositoryEntity>()


        //check cached keys
        val keys = withContext(Dispatchers.IO) {
            dao.getRepoKeys(startLimit, offset)
        }

        if (keys.isEmpty()) {
            //fetching keys from remote
            var offline = false
            try {
                getAndCacheKeys()
            } catch (e: Exception) {
                Log.e(TAG, "Failed to fetch data from remote")
                offline = true
            }
            if (!offline) {
                withContext(Dispatchers.IO) {
                    dao.getRepoKeys(startLimit, offset).forEach { repo ->
                        val parameters = repo.fullName.split("/")
                        service?.getDetailedRepository(parameters[0], parameters[1])
                            ?.let { detailedReposList.add(it) }
                    }

                }
            }

        } else {
            try {
                withContext(Dispatchers.IO) {
                    keys.forEach { repo ->
                        val parameters = repo.fullName.split("/")

                        service?.getDetailedRepository(parameters[0], parameters[1])
                            ?.let { detailedReposList.add(it) }

                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Failed to fetch data from remote")
            }
        }


        if (detailedReposList.isNotEmpty()) {
            //caching repos
            withContext(Dispatchers.IO) {
                dao.insertDetailedRepos(detailedReposList)
            }
        }

        return detailedReposList
    }

    suspend fun getDetailedRepos(startLimit: Int, offset: Int): List<DetailedRepositoryEntity> {
        var list = getAndCacheRepos(startLimit, offset)
        if (list.isEmpty()) {
            list = withContext(Dispatchers.IO) {
                dao.getDetailedRepos(startLimit, offset)
            }
        }
        if (list.isEmpty()) {
            list = listOf(NetworkStateHelper.repoFailureObject)
        }
        return list
    }

    suspend fun getMaxLimit(): Int {
        return withContext(Dispatchers.IO) {
            dao.getMaxLimit()
        }
    }


    suspend fun getAllIssues(ownerName: String, repoName: String): List<IssueEntity> {
        return withContext(Dispatchers.IO) {
            try {
                service!!.getIssues(ownerName, repoName)
            }catch (e:Exception){
                listOf(NetworkStateHelper.issuesFailureObject)
            }

        }
    }

}
