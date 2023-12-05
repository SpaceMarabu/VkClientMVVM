package com.example.vknewsclient.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.DismissDirection
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vknewsclient.MainViewModel
import com.example.vknewsclient.domain.FeedPost
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(viewModel: MainViewModel) {

    val selectedNavItem by viewModel.selectedNavItem.observeAsState(NavigationItem.Home)

    Scaffold(
        bottomBar = {
            BottomNavigation {
                val items = listOf(
                    NavigationItem.Home,
                    NavigationItem.Favourite,
                    NavigationItem.Profile
                )
                items.forEach {item ->
                    BottomNavigationItem(
                        selected = selectedNavItem == item,
                        onClick = { viewModel.selectNavItem(item) },
                        icon = {
                            Icon(item.icon, contentDescription = null)
                        },
                        label = {
                            Text(text = stringResource(id = item.titleResId))
                        },
                        selectedContentColor = MaterialTheme.colors.onPrimary,
                        unselectedContentColor = MaterialTheme.colors.onSecondary
                    )
                }
            }
        }
    ) {
        val feedPosts = viewModel.feedPosts.observeAsState(listOf())

        LazyColumn(
            contentPadding = PaddingValues(
                top = 16.dp,
                start = 8.dp,
                end = 8.dp,
                bottom = 72.dp
            ),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            content = {
                items(feedPosts.value, key = { it.id }) { currentFeedPost ->

                    val dismissState = rememberDismissState()

                    if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                        viewModel.delete(currentFeedPost)
                    }

                    SwipeToDismiss(
                        modifier = Modifier.animateItemPlacement(),
                        state = dismissState,
                        directions = setOf(DismissDirection.EndToStart),
                        background = {
                            Box(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxSize()
                                    .background(Color.Red.copy(alpha = 0.5f)),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                Text(
                                    text = "Delete item",
                                    color = Color.White,
                                    fontSize = 24.sp
                                )
                            }
                        }
                    ) {
                        PostCard(
                            feedPost = currentFeedPost,
                            onLikeClickListener = {
                                viewModel.updateCount(currentFeedPost, it)
                            },
                            onShareClickListener = {
                                viewModel.updateCount(currentFeedPost, it)
                            },
                            onViewsClickListener = {
                                viewModel.updateCount(currentFeedPost, it)
                            },
                            onCommentClickListener = {
                                viewModel.updateCount(currentFeedPost, it)
                            },
                        )
                    }
                }
            })
    }
}