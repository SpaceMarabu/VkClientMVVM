package com.example.vknewsclient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vknewsclient.domain.FeedPost
import com.example.vknewsclient.domain.StatisticItem
import com.example.vknewsclient.ui.theme.NavigationItem
import java.lang.IllegalStateException

class MainViewModel : ViewModel() {

    private val initialList = mutableListOf<FeedPost>().apply {
        repeat(5) {
            add(
                FeedPost(id = it)
            )
        }
    }

    private val _feedPosts = MutableLiveData<List<FeedPost>>(initialList)
    val feedPosts: LiveData<List<FeedPost>> = _feedPosts

    private val _selectedNavItem = MutableLiveData<NavigationItem>(NavigationItem.Home)
    val selectedNavItem: LiveData<NavigationItem> = _selectedNavItem

    fun selectNavItem(item: NavigationItem) {
        _selectedNavItem.value = item
    }

    fun updateCount(feedPost: FeedPost, item: StatisticItem) {
        val oldPosts = feedPosts.value?.toMutableList() ?: mutableListOf()
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
        _feedPosts.value = oldPosts.apply {
            replaceAll {
                if (it.id == newFeedPost.id) {
                    newFeedPost
                } else {
                    it
                }
            }
        }
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
        val modifiedList = feedPosts.value?.toMutableList() ?: mutableListOf()
        modifiedList.remove(feedPost)
        _feedPosts.value = modifiedList
    }
}