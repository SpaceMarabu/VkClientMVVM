package com.example.vknewsclient.data

import com.example.vknewsclient.domain.StatisticItem
import com.example.vknewsclient.domain.StatisticType

data class PostStatistics (
    val views: Int,
    val shares: Int,
    val comments: Int,
    val likes: Int
) {

    fun getPostStatistics(): List<StatisticItem> {
        return listOf(
            StatisticItem(type = StatisticType.VIEWS, views),
            StatisticItem(type = StatisticType.SHARES, shares),
            StatisticItem(type = StatisticType.COMMENTS, comments),
            StatisticItem(type = StatisticType.LIKES, likes)
        )
    }
}