package com.kinglloy.android.github.ui.common;

import android.view.ViewGroup;

import com.kinglloy.android.github.databinding.RepoItemBinding;
import com.kinglloy.android.github.vo.Repo;

/**
 * A RecyclerView adapter for {@link Repo} class.
 *
 * @author jinyalin
 * @since 2018/2/9.
 */

public class RepoListAdapter extends DataBoundListAdapter<Repo, RepoItemBinding> {
    @Override
    protected RepoItemBinding createBinding(ViewGroup parent) {
        return null;
    }

    @Override
    protected void bind(RepoItemBinding binding, Repo item) {

    }

    @Override
    protected boolean areItemsTheSame(Repo oldItem, Repo newItem) {
        return false;
    }

    @Override
    protected boolean areContentsTheSame(Repo oldItem, Repo newItem) {
        return false;
    }
}
