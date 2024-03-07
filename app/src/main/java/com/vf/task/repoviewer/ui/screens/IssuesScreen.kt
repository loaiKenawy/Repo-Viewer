package com.vf.task.repoviewer.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.vf.task.repoviewer.data.model.IssueEntity
import com.vf.task.repoviewer.ui.common.CustomActionBar
import com.vf.task.repoviewer.ui.common.DrawLine
import com.vf.task.repoviewer.ui.common.ErrorCard
import com.vf.task.repoviewer.ui.common.ProgressBarConfig
import com.vf.task.repoviewer.ui.common.cardsBackground
import com.vf.task.repoviewer.ui.common.mainGradientBackground
import com.vf.task.repoviewer.ui.theme.Alto
import com.vf.task.repoviewer.ui.theme.LightSharkGray
import com.vf.task.repoviewer.ui.theme.SharkGray
import com.vf.task.repoviewer.ui.viewmodels.IssuesViewModel
import com.vf.task.repoviewer.util.IssueDataFormatter
import com.vf.task.repoviewer.util.NetworkStateHelper


private lateinit var viewModel: IssuesViewModel
private var fetch = true

@Composable
fun IssuesScreen(owner: String, name: String) {
    Log.d("IssuesScreen", "$owner   $name")
    viewModel =
        ViewModelProvider(LocalViewModelStoreOwner.current!!)[IssuesViewModel::class.java]

    LaunchedEffect(Unit) {
        viewModel.getIssues(owner, name)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        CustomActionBar("$owner/$name issues")
        viewModel.issues.observeAsState().value.let {
            if (it.isNullOrEmpty()) {
                ProgressBarConfig()
            } else {
                if (it[0].id == NetworkStateHelper.networkFailureCode){
                    ErrorCard()
                }else{
                    IssueList(issues = it)
                }
            }
        }
    }

}


@Composable
fun IssueCard(issueItem: IssueEntity) {
    Box(
        modifier = Modifier
            .cardsBackground()
    ) {
        Box(modifier = Modifier.padding(10.dp)) {
            Column(modifier = Modifier.padding(5.dp)) {
                Text(
                    text = issueItem.title,
                    modifier = Modifier
                        .padding(start = 3.dp)
                        .background(Color.Transparent)
                        .height(30.dp),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = SharkGray
                )
                Text(
                    text = IssueDataFormatter.formatDateAndName(issueItem.date,issueItem.author.name),
                    modifier = Modifier
                        .padding(start = 3.dp)
                        .background(Color.Transparent)
                        .wrapContentHeight(),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light,
                    color = Alto
                )

                DrawLine(color = LightSharkGray, height = 1)
                Spacer(modifier = Modifier.height(4.dp))

                Box(
                    modifier = Modifier
                        .cardsBackground()
                        .fillMaxWidth()
                        .padding(5.dp)
                ) {
                    Text(
                        text = issueItem.state,
                        modifier = Modifier
                            .padding(start = 3.dp)
                            .background(Color.Transparent)
                            .fillMaxWidth(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = SharkGray,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun IssueList(issues: List<IssueEntity>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier

            .mainGradientBackground()
            .padding(16.dp)
    ) {

        items(issues) {
            IssueCard(issueItem = it)
        }
    }
}

@Preview
@Composable
fun Preview() {

}