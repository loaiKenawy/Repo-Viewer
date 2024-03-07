package com.vf.task.repoviewer.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ForkLeft
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.SnippetFolder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Subscriptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.vf.task.repoviewer.data.model.DetailedRepositoryEntity
import com.vf.task.repoviewer.ui.common.CounterShape
import com.vf.task.repoviewer.ui.common.CustomActionBar
import com.vf.task.repoviewer.ui.common.DrawLine
import com.vf.task.repoviewer.ui.common.cardsBackground
import com.vf.task.repoviewer.ui.common.mainGradientBackground
import com.vf.task.repoviewer.ui.navigation.Screen
import com.vf.task.repoviewer.ui.theme.Alto
import com.vf.task.repoviewer.ui.theme.SharkGray
import com.vf.task.repoviewer.ui.viewmodels.ReposViewModel

private var owner = ""
private var name = ""


@Composable
fun DetailsScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .mainGradientBackground()
    ) {
        CustomActionBar("Repo Details")
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(start = 10.dp, end = 10.dp, top = 50.dp)
        ) {

            ReposViewModel.selectedRepo.value?.let {
                val res = it.fullName.split("/")
                owner = res[0]
                name = res[1]
                RepoDetailsCard(it)
            }
        }
        Button(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp, start = 10.dp, end = 10.dp)
            .background(Color.Transparent), shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, Color.White),
            colors = ButtonDefaults.buttonColors(SharkGray),
            onClick = {

                navController.navigate(Screen.IssuesScreen.withArgs(owner, name))
            }) {
            Text(
                text = "issues",
                modifier = Modifier
                    .padding(top = 20.dp, bottom = 20.dp)
                    .background(Color.Transparent),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Alto
            )
        }
    }
}

@Composable
fun RepoDetailsCard(repo: DetailedRepositoryEntity) {
    Box(
        modifier = Modifier
            .cardsBackground()
    ) {
        Box(modifier = Modifier.padding(10.dp)) {

            Column() {

                //Repository Name and owner name
                Box {
                    Row(modifier = Modifier.padding(5.dp)) {
                        Icon(
                            tint = SharkGray,
                            modifier = Modifier.size(30.dp),
                            imageVector = Icons.Default.SnippetFolder,
                            contentDescription = ""
                        )
                        Text(
                            text = repo.fullName,
                            modifier = Modifier
                                .padding(start = 3.dp)
                                .background(Color.Transparent)
                                .height(30.dp),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = SharkGray
                        )
                    }
                }

                // Line
                DrawLine(color = SharkGray, height = 2)
                Spacer(modifier = Modifier.height(3.dp))

                //Row of counters
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CounterShape(
                        counterValue = repo.starsCount.toString(),
                        counterName = "stars",
                        icon = Icons.Default.Star
                    )
                    CounterShape(
                        counterValue = repo.issuesCount.toString(),
                        counterName = "issues",
                        icon = Icons.Default.PushPin
                    )
                    CounterShape(
                        counterValue = repo.forks.toString(),
                        counterName = "forks",
                        icon = Icons.Default.ForkLeft
                    )
                    CounterShape(
                        counterValue = repo.subscribersCount.toString(),
                        counterName = "followers",
                        icon = Icons.Default.Subscriptions
                    )
                }

                Spacer(modifier = Modifier.height(3.dp))
                DrawLine(color = SharkGray, height = 1)
                Spacer(modifier = Modifier.height(3.dp))

                //Description Box
                Box(modifier = Modifier.padding(5.dp)) {
                    var description = "No Description Found"
                    if (repo.description != null) {
                        description = repo.description
                    }
                    Text(
                        text = description,
                        modifier = Modifier
                            .background(Color.Transparent)
                            .defaultMinSize(minHeight = 400.dp),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Alto

                    )
                }
            }
        }
    }
}


