package com.kinglloy.android.pagingwithnetwork.sample.reddit.repository

import com.kinglloy.android.pagingwithnetwork.sample.reddit.vo.RedditPost

/**
 * Common interface shared by the different repository implementations.
 * Note: this only exists for sample purposes - typically an app would implement a repo once, either
 * network+db, or network-only
 * @author jinyalin
 * @since 2018/1/26.
 */

interface RedditPostRepository {
    fun postsOfSubreddit(subredditName: String, pageSize: Int): Listing<RedditPost>

    enum class Type {
        IN_MEMORY_BY_ITEM,
        IN_MEMORY_BY_PAGE,
        DB
    }
}
