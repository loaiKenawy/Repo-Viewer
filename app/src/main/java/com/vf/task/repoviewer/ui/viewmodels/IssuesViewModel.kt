package com.vf.task.repoviewer.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vf.task.repoviewer.data.local.Graph
import com.vf.task.repoviewer.data.model.IssueEntity
import com.vf.task.repoviewer.data.repository.ReposRepository

class IssuesViewModel(private val repository: ReposRepository = Graph.repository) : ViewModel() {

    val issues = MutableLiveData<List<IssueEntity>>()

    suspend fun getIssues(owner: String, name: String) {
            issues.value = repository.getAllIssues(owner, name)
    }
}