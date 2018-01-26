package com.kinglloy.android.pagingwithnetwork.sample.reddit.ui

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.kinglloy.android.pagingwithnetwork.sample.R
import com.kinglloy.android.pagingwithnetwork.sample.reddit.ServiceLocator
import com.kinglloy.android.pagingwithnetwork.sample.reddit.repository.RedditPostRepository

/**
 * A list activity that shows reddit posts in the given sub-reddit.
 * <p>
 * The intent arguments can be modified to make it use a different repository (see MainActivity).
 * @author jinyalin
 * @since 2018/1/26.
 */
class RedditActivity : AppCompatActivity() {
    companion object {
        val KEY_SUBREDDIT = "subreddit"
        val DEFAULT_SUBREDDIT = "androiddev"
        val KEY_REPOSITORY_TYPE = "repository_type"
        fun intentFor(context: Context, type: RedditPostRepository.Type): Intent {
            val intent = Intent(context, RedditActivity::class.java)
            intent.putExtra(KEY_REPOSITORY_TYPE, type.ordinal)
            return intent
        }
    }

    private lateinit var model: SubRedditViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reddit)

        model = getViewModel()
    }

    private fun getViewModel(): SubRedditViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val repoTypeParam = intent.getIntExtra(KEY_REPOSITORY_TYPE, 0)
                val repoType = RedditPostRepository.Type.values()[repoTypeParam]
                val repo = ServiceLocator.instance(this@RedditActivity)
                        .getRepository(repoType)

                @Suppress("UNCHECKED_CAST")
                return SubRedditViewModel(repo) as T
            }
        })[SubRedditViewModel::class.java]
    }
}