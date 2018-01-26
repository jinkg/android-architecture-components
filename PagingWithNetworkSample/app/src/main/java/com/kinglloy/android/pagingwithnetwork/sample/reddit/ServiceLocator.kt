package com.kinglloy.android.pagingwithnetwork.sample.reddit

import android.app.Application
import android.content.Context
import android.support.annotation.VisibleForTesting
import com.kinglloy.android.pagingwithnetwork.sample.reddit.api.RedditApi
import com.kinglloy.android.pagingwithnetwork.sample.reddit.db.RedditDb
import com.kinglloy.android.pagingwithnetwork.sample.reddit.repository.RedditPostRepository
import com.kinglloy.android.pagingwithnetwork.sample.reddit.repository.inDb.DbRedditPostRepository
import com.kinglloy.android.pagingwithnetwork.sample.reddit.repository.inMemory.byItem.InMemoryByItemRepository
import com.kinglloy.android.pagingwithnetwork.sample.reddit.repository.inMemory.byPage.InMemoryByPageKeyRepository
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * @author jinyalin
 * @since 2018/1/26.
 */
interface ServiceLocator {
    companion object {
        private val LOCK = Any()
        private var instance: ServiceLocator? = null

        fun instance(context: Context): ServiceLocator =
                instance ?: synchronized(this) {
                    instance ?: DefaultServiceLocator(
                            app = context.applicationContext as Application,
                            useInMemoryDb = false).also { instance = it }
                }

        /**
         * Allows tests to replace the default implementations.
         */
        @VisibleForTesting
        fun swap(locator: ServiceLocator) {
            instance = locator
        }
    }

    fun getRepository(type: RedditPostRepository.Type): RedditPostRepository

    fun getNetworkExecutor(): Executor

    fun getDiskIOExecutor(): Executor

    fun getRedditApi(): RedditApi
}

open class DefaultServiceLocator(val app: Application, val useInMemoryDb: Boolean) : ServiceLocator {
    // thread pool used for disk access
    @Suppress("PrivatePropertyName")
    private val DISK_IO = Executors.newSingleThreadExecutor()

    // thread pool used for network requests
    @Suppress("PrivatePropertyName")
    private val NETWORK_IO = Executors.newFixedThreadPool(5)

    private val db by lazy {
        RedditDb.create(app, useInMemoryDb)
    }

    private val api by lazy {
        RedditApi.create()
    }

    override fun getRepository(type: RedditPostRepository.Type): RedditPostRepository {
        return when (type) {
            RedditPostRepository.Type.IN_MEMORY_BY_ITEM -> InMemoryByItemRepository(
                    redditApi = getRedditApi(),
                    networkExecutor = getNetworkExecutor())
            RedditPostRepository.Type.IN_MEMORY_BY_PAGE -> InMemoryByPageKeyRepository(
                    redditApi = getRedditApi(),
                    networkExecutor = getNetworkExecutor())
            RedditPostRepository.Type.DB -> DbRedditPostRepository(
                    db = db,
                    redditApi = getRedditApi(),
                    ioExecutor = getDiskIOExecutor())
        }
    }

    override fun getNetworkExecutor(): Executor = NETWORK_IO

    override fun getDiskIOExecutor(): Executor = DISK_IO

    override fun getRedditApi() = api

}