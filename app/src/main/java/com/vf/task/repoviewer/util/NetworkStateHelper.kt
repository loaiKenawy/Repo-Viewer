package com.vf.task.repoviewer.util

import com.vf.task.repoviewer.data.model.Author
import com.vf.task.repoviewer.data.model.DetailedRepositoryEntity
import com.vf.task.repoviewer.data.model.IssueEntity

class NetworkStateHelper {
    companion object NetworkState {
        const val networkFailureCode = -99999
        val repoFailureObject = DetailedRepositoryEntity(networkFailureCode, "", "", 0, 0, 0, 0)
        var issuesFailureObject = IssueEntity(networkFailureCode, "", "", "", Author(0, ""))
    }
}