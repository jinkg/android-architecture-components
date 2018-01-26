package com.kinglloy.android.pagingwithnetwork.sample.reddit.repository.inMemory.byItem

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.kinglloy.android.pagingwithnetwork.sample.reddit.api.RedditApi
import com.kinglloy.android.pagingwithnetwork.sample.reddit.vo.RedditPost
import java.util.concurrent.Executor

/**
 * A simple data source factory which also provides a way to observe the last created data source.
 * This allows us to channel its network request status etc back to the UI. See the Listing creation
 * in the Repository class.
 * @author jinyalin
 * @since 2018/1/26.
 */
class SubRedditDataSourceFactory(
        private val redditApi: RedditApi,
        private val subredditName: String,
        private val retryExecutor: Executor) : DataSource.Factory<String, RedditPost> {
    val sourceLiveData = MutableLiveData<ItemKeyedSubredditDataSource>()
    override fun create(): DataSource<String, RedditPost> {
        val source = ItemKeyedSubredditDataSource(redditApi, subredditName, retryExecutor)
        sourceLiveData.postValue(source)
        return source
    }
}