package com.vf.task.repoviewer.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vf.task.repoviewer.data.local.Graph
import com.vf.task.repoviewer.data.model.DetailedRepositoryEntity
import com.vf.task.repoviewer.data.repository.ReposRepository
import com.vf.task.repoviewer.util.NetworkStateHelper
import kotlinx.coroutines.launch

class ReposViewModel(private val repository: ReposRepository = Graph.repository) :
    ViewModel() {

    private var startLimit = 0

    var errorFlag = MutableLiveData<Boolean>()
    var mRepos = MutableLiveData<List<DetailedRepositoryEntity>>()

    init {
        viewModelScope.launch {
            getRepos()
        }
    }

    private suspend fun getRepos() {
        val response = repository.getDetailedRepos(startLimit, 5)
        if (response[0].id != NetworkStateHelper.networkFailureCode) {
            errorFlag.value = false
            if (mRepos.value.isNullOrEmpty()) {
                mRepos.value = response
            } else {
                mRepos.value = mRepos.value?.plus(response)
            }
        } else {
            errorFlag.value = true
        }
    }

    fun loadMoreRepositories() {
        startLimit += 5
        viewModelScope.launch {
            if (startLimit < repository.getMaxLimit()) {
                getRepos()
            }
        }
    }

    fun onRpoSelected(repo: DetailedRepositoryEntity) {
        selectedRepo.value = repo
    }

    companion object {
        var selectedRepo = mutableStateOf<DetailedRepositoryEntity?>(null)
    }
}
