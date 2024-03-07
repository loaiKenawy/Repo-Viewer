package com.vf.task.repoviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.vf.task.repoviewer.data.local.Graph
import com.vf.task.repoviewer.ui.navigation.Navigation
import com.vf.task.repoviewer.ui.theme.RepoViewerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Graph.provide(applicationContext)
            RepoViewerTheme {
              Navigation()

            }
        }
    }
}
