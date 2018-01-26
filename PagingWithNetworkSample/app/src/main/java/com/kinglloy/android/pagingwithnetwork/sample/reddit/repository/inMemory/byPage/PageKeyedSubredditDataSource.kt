package com.kinglloy.android.pagingwithnetwork.sample.reddit.repository.inMemory.byPage

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PageKeyedDataSource
import com.kinglloy.android.pagingwithnetwork.sample.reddit.api.RedditApi
import com.kinglloy.android.pagingwithnetwork.sample.reddit.repository.NetworkState
import com.kinglloy.android.pagingwithnetwork.sample.reddit.vo.RedditPost
import retrofit2.Call
import retrofit2.Response
import java.io.IOException
import java.util.concurrent.Executor

/**
 * A data source that uses the before/after keys returned in page requests.
 * <p>
 * See ItemKeyedSubredditDataSource
 * @author jinyalin
 * @since 2018/1/26.
 */
class PageKeyedSubredditDataSource(
        private val redditApi: RedditApi,
        private val subredditName: String,
        private val retryExecutor: Executor) : PageKeyedDataSource<String, RedditPost>() {
    // keep a function reference for the retry event
    private var retry: (() -> Any)? = null

    /**
     * There is no sync on the state because paging will always call loadInitial first then wait
     * for it to return some success value before calling loadAfter.
     */
    val networkState = MutableLiveData<NetworkState>()

    val initialLoad = MutableLiveData<NetworkState>()

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.let {
            retryExecutor.execute {
                it.invoke()
            }
        }
    }

    override fun loadBefore(params: LoadParams<String>,
                            callback: LoadCallback<String, RedditPost>) {
        // ignored, since we only ever append to our initial load
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, RedditPost>) {
        networkState.postValue(NetworkState.LOADING)
        redditApi.getTopAfter(subreddit = subredditName,
                after = params.key,
                limit = params.requestedLoadSize).enqueue(
                object : retrofit2.Callback<RedditApi.ListingResponse> {
                    override fun onResponse(call: Call<RedditApi.ListingResponse>,
                                            response: Response<RedditApi.ListingResponse>) {
                        if (response.isSuccessful) {
                            val data = response.body()?.data
                            val items = data?.children?.map { it.data } ?: emptyList()
                            retry = null
                            callback.onResult(items, data?.after)
                            networkState.postValue(NetworkState.LOADED)
                        } else {
                            retry = {
                                loadAfter(params, callback)
                            }
                            networkState.postValue(
                                    NetworkState.error("error code: ${response.code()}")
                            )
                        }
                    }

                    override fun onFailure(call: Call<RedditApi.ListingResponse>, t: Throwable) {
                        retry = {
                            loadAfter(params, callback)
                        }
                        networkState.postValue(NetworkState.error(t.message ?: "unknown error"))
                    }
                }
        )
    }

    override fun loadInitial(params: LoadInitialParams<String>,
                             callback: LoadInitialCallback<String, RedditPost>) {
        val request = redditApi.getTop(
                subreddit = subredditName,
                limit = params.requestedLoadSize
        )
        networkState.postValue(NetworkState.LOADING)
        networkState.postValue(NetworkState.LOADING)

        // triggered by a refresh, we better execute sync
        try {
            val response = request.execute()
            val data = response.body()?.data
            val items = data?.children?.map { it.data } ?: emptyList()
            retry = null
            networkState.postValue(NetworkState.LOADED)
            initialLoad.postValue(NetworkState.LOADED)
            callback.onResult(items, data?.before, data?.after)
        } catch (ioException: IOException) {
            retry = {
                loadInitial(params, callback)
            }
            val error = NetworkState.error(ioException.message ?: "unknown error")
            networkState.postValue(error)
            initialLoad.postValue(error)
        }
    }
}
