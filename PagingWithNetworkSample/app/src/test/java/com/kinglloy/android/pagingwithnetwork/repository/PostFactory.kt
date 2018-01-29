package com.kinglloy.android.pagingwithnetwork.repository

import com.kinglloy.android.pagingwithnetwork.sample.reddit.vo.RedditPost
import java.util.concurrent.atomic.AtomicInteger

/**
 * @author jinyalin
 * @since 2018/1/29.
 */
class PostFactory {
    private val counter = AtomicInteger(0)
    fun createRedditPost(subredditName: String): RedditPost {
        val id = counter.incrementAndGet()
        val post = RedditPost(
                name = "name_$id",
                title = "title $id",
                score = 10,
                author = "author $id",
                num_comments = 0,
                created = System.currentTimeMillis(),
                thumbnail = null,
                subreddit = subredditName,
                url = null)
        post.indexInResponse = -1
        return post
    }
}