package com.vf.task.repoviewer.data.model

import com.google.gson.annotations.SerializedName

data class IssueEntity(
    @SerializedName("id")
    val id:Int,
    @SerializedName("title")
    val title :String,
    @SerializedName("state")
    val state :String,
    @SerializedName("created_at")
    val date : String,
    @SerializedName("user")
    val author: Author

)

data class Author(
    @SerializedName("id")
    val id: Int,
    @SerializedName("login")
    val name:String
)
