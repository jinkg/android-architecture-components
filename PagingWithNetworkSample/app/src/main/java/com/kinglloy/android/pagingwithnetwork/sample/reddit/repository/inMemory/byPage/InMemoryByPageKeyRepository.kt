package com.kinglloy.android.pagingwithnetwork.sample.reddit.repository.inMemory.byPage

import android.arch.lifecycle.Transformations
import android.arch.paging.LivePagedListBuilder
import android.support.annotation.MainThread
import com.kinglloy.android.pagingwithnetwork.sample.reddit.api.RedditApi
import com.kinglloy.android.pagingwithnetwork.sample.reddit.repository.Listing
import com.kinglloy.android.pagingwithnetwork.sample.reddit.repository.RedditPostRepository
import com.kinglloy.android.pagingwithnetwork.sample.reddit.vo.RedditPost
import java.util.concurrent.Executor

/**
 * Repository implementation that returns a Listing that loads data directly from network by using
 * the previous / next page keys returned in the query.
 * @author jinyalin
 * @since 2018/1/26.
 */
class InMemoryByPageKeyRepository(private val redditApi: RedditApi,
                                  private val networkExecutor: Executor) : RedditPostRepository {
    @MainThread
    override fun postsOfSubreddit(subredditName: String, pageSize: Int): Listing<RedditPost> {
        val sourceFactory = SubRedditDataSourceFactory(redditApi, subredditName, networkExecutor)

        val livePagedList = LivePagedListBuilder(sourceFactory, pageSize)
                // provide custom executor for network requests, otherwise it will default to
                // Arch Components' IO pool which is also used for disk access
                .setBackgroundThreadExecutor(networkExecutor)
                .build()

        val refreshState = Transformations.switchMap(sourceFactory.sourceLiveData) {
            it.initialLoad
        }

        return Listing(pagedList = livePagedList,
                networkState = Transformations.switchMap(sourceFactory.sourceLiveData, {
                    it.networkState
                }),
                retry = {
                    sourceFactory.sourceLiveData.value?.retryAllFailed()
                },
                refresh = {
                    sourceFactory.sourceLiveData.value?.invalidate()
                },
                refreshState = refreshState
        )
    }

}