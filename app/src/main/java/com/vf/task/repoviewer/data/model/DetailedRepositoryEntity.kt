package com.vf.task.repoviewer.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "DetailedRepositories")
data class DetailedRepositoryEntity(

    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    val id: Int,

    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("stargazers_count")
    val starsCount: Int,
    @SerializedName("open_issues_count")
    val issuesCount: Int,
    @SerializedName("forks")
    val forks: Int,
    @SerializedName("subscribers_count")
    val subscribersCount: Int
)
