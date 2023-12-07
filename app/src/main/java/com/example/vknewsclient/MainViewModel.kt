package com.example.vknewsclient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vknewsclient.domain.FeedPost
import com.example.vknewsclient.domain.PostComment
import com.example.vknewsclient.domain.StatisticItem
import com.example.vknewsclient.ui.theme.HomeScreenState
import com.example.vknewsclient.ui.theme.NavigationItem

class MainViewModel : ViewModel() {

    private val initialList = mutableListOf<FeedPost>().apply {
        repeat(5) {
            add(
                FeedPost(id = it)
            )
        }
    }

    private val comments = mutableListOf<PostComment>().apply {
        repeat(10) {
            add(PostComment(id = it))
        }
    }

    private val initialState = HomeScreenState.Posts(posts = initialList)

    private val _screenState = MutableLiveData<HomeScreenState>(initialState)
    val screenState: LiveData<HomeScreenState> = _screenState

    private var savedState: HomeScreenState? = initialState


    fun updateCount(feedPost: FeedPost, item: StatisticItem) {
        val currentState = screenState.value
        if (currentState !is HomeScreenState.Posts) return

        val oldPosts = currentState.posts.toMutableList()
        val oldStatistics = feedPost.statistics
        val newStatistics = oldStatistics.toMutableList().apply {
            replaceAll { oldItem ->
                if (oldItem.type == item.type) {
                    oldItem.copy(count = oldItem.count + 1)
                } else {
                    oldItem
                }
            }
        }
        val newFeedPost = feedPost.copy(statistics = newStatistics)
        val newPosts = oldPosts.apply {
            replaceAll {
                if (it.id == newFeedPost.id) {
                    newFeedPost
                } else {
                    it
                }
            }
        }
        _screenState.value = HomeScreenState.Posts(posts = newPosts)
    }

    fun showComments(feedPost: FeedPost) {
        savedState = _screenState.value
        _screenState.value = HomeScreenState.Comments(comments = comments, feedPost = feedPost)
    }

    fun closeComments() {
        _screenState.value = savedState
    }

//    private fun updateCount(feedPost: FeedPost, item: StatisticItem): FeedPost {
//        val oldStatistics = feedPost.statistics ?: throw IllegalStateException()
//        val newStatistics = oldStatistics.toMutableList().apply {
//            replaceAll {oldItem ->
//                if (oldItem.type == item.type) {
//                    oldItem.copy(count = oldItem.count + 1)
//                } else {
//                    oldItem
//                }
//            }
//        }
//        return feedPost.copy(statistics = newStatistics)
//    }
//
//    fun updateModel(model: PostModel, item: StatisticItem) {
//        val modifiedList = _models.value?.toMutableList() ?: mutableListOf()
//        modifiedList.replaceAll {
//            if (it == model) {
//                val oldFeedPost = it.feedPost
//                val newFeedPost = updateCount(oldFeedPost, item)
//                it.copy(feedPost = newFeedPost)
//            } else {
//                it
//            }
//        }
//        _models.value = modifiedList
//    }

    fun delete(feedPost: FeedPost) {
        val currentState = screenState.value
        if (currentState !is HomeScreenState.Posts) return

        val oldPosts = currentState.posts.toMutableList() ?: mutableListOf()
        oldPosts.remove(feedPost)
        _screenState.value = HomeScreenState.Posts(posts = oldPosts)
    }
}