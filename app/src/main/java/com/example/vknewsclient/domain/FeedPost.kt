package com.example.vknewsclient.domain

import androidx.compose.ui.res.stringResource
import com.example.vknewsclient.R
import kotlin.random.Random

data class FeedPost(
    val id: Int = 0,
    val comunityName: String = "/dev/null",
    val publicationDate: String = "14:00",
    val avatarResId: Int = R.drawable.post_comunity_thumbnail,
    val contentText: String = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
    val contentImageResId: Int = R.drawable.post_content_image,
    val statistics: List<StatisticItem> = listOf(
        StatisticItem(type = StatisticType.VIEWS, Random.nextInt(100)),
        StatisticItem(type = StatisticType.SHARES, Random.nextInt(100)),
        StatisticItem(type = StatisticType.COMMENTS, Random.nextInt(100)),
        StatisticItem(type = StatisticType.LIKES, Random.nextInt(100))
    )
)