package com.vf.task.repoviewer.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SnippetFolder
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavController
import com.vf.task.repoviewer.data.model.DetailedRepositoryEntity
import com.vf.task.repoviewer.ui.common.CustomActionBar
import com.vf.task.repoviewer.ui.common.DrawLine
import com.vf.task.repoviewer.ui.common.ErrorCard
import com.vf.task.repoviewer.ui.common.ProgressBarConfig
import com.vf.task.repoviewer.ui.common.cardsBackground
import com.vf.task.repoviewer.ui.common.mainGradientBackground
import com.vf.task.repoviewer.ui.navigation.Screen
import com.vf.task.repoviewer.ui.theme.Alto
import com.vf.task.repoviewer.ui.theme.LightSharkGray
import com.vf.task.repoviewer.ui.theme.SharkGray
import com.vf.task.repoviewer.ui.viewmodels.ReposViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest


private lateinit var viewModel: ReposViewModel


@Composable
fun MainScreen(navController: NavController) {
    viewModel =
        ViewModelProvider(LocalViewModelStoreOwner.current!!)[ReposViewModel::class.java]
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        CustomActionBar("Repositories")
        viewModel.mRepos.observeAsState().value.let { reposList ->
            if (reposList.isNullOrEmpty()) {
                viewModel.errorFlag.observeAsState().value.let {
                    if (it == true) {
                        ErrorCard()
                    } else {
                        ProgressBarConfig()
                    }
                }
            } else {
                viewModel.errorFlag.observeAsState().value.let {
                    if (it == true) {
                        RepoList(repositories = reposList, navController)
                        Toast.makeText(LocalContext.current, "Connection Error", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        RepoList(repositories = reposList, navController)
                    }
                }
            }
        }
    }
}


@Composable
private fun RepoCard(repoItem: DetailedRepositoryEntity, navController: NavController) {

    Box(modifier = Modifier
        .padding(top = 15.dp, start = 15.dp, end = 15.dp)
        .cardsBackground()
        .clickable {
            viewModel.onRpoSelected(repoItem)
            navController.navigate(Screen.DetailsScreen.route)
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 10.dp, start = 15.dp, end = 15.dp)

        ) {
            Column() {
                //Repository Name and owner name
                Box {
                    Row {
                        Box(modifier = Modifier.padding(5.dp)) {
                            Icon(
                                tint = SharkGray,
                                modifier = Modifier.size(30.dp),
                                imageVector = Icons.Default.SnippetFolder,
                                contentDescription = ""
                            )
                        }
                        Text(
                            text = repoItem.fullName,
                            modifier = Modifier
                                .background(Color.Transparent),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = SharkGray
                        )
                    }

                }

                // Line
                DrawLine(color = SharkGray, height = 2)

                //Description Box
                Box(modifier = Modifier.padding(5.dp)) {
                    var description = "No Description Found"
                    if (repoItem.description != null) {
                        description = repoItem.description
                    }
                    Text(
                        text = description,
                        modifier = Modifier
                            .padding(bottom = 5.dp, start = 15.dp, end = 15.dp)
                            .background(Color.Transparent),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Alto

                    )
                }

                // Line
                DrawLine(color = SharkGray, height = 1)

                //Stars Count Box
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(5.dp)
                ) {
                    Row(modifier = Modifier.align(alignment = Alignment.CenterEnd)) {
                        Box(modifier = Modifier.padding(5.dp)) {
                            Icon(
                                tint = LightSharkGray,
                                modifier = Modifier.size(22.dp),
                                imageVector = Icons.Default.StarBorder, contentDescription = ""
                            )
                        }
                        Text(
                            text = repoItem.starsCount.toString(),
                            modifier = Modifier
                                .background(Color.Transparent)
                                .padding(top = 6.dp, bottom = 6.dp),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = LightSharkGray,


                            )
                    }


                }
            }
        }
    }
}


@Composable
private fun RepoList(
    repositories: List<DetailedRepositoryEntity>,
    navController: NavController
) {

    val more = remember { mutableStateOf(1) }
    val listState = rememberLazyListState()
    val loading = remember { mutableStateOf(false) }


    LazyColumn(
        state = listState,
        modifier = Modifier
            .mainGradientBackground()
    ) {

        items(repositories) {
            RepoCard(repoItem = it, navController)
        }
        item {
            if (loading.value) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(50.dp), strokeWidth = 2.dp)
                }
            }
        }
    }


    LaunchedEffect(key1 = more.value) {
        loading.value = true
        delay(1000)
        viewModel.loadMoreRepositories()
        loading.value = false
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collectLatest { index ->
                val size = viewModel.mRepos.value?.size
                if (size != null) {
                    if (index != null && index == size - 1) {
                        more.value++
                    }
                }
            }
    }
}

