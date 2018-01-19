package com.kinglloy.example.android.persistence.ui;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.kinglloy.example.android.persistence.R;
import com.kinglloy.example.android.persistence.databinding.CommentItemBinding;
import com.kinglloy.example.android.persistence.model.Comment;

import java.util.List;

/**
 * @author jinyalin
 * @since 2018/1/19.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private List<? extends Comment> mCommentList;

    @Nullable
    private final CommentClickCallback mCommentClickCallback;

    public CommentAdapter(CommentClickCallback commentClickCallback) {
        this.mCommentClickCallback = commentClickCallback;
    }

    public void setCommentList(final List<? extends Comment> comments) {
        if (mCommentList == null) {
            mCommentList = comments;
            notifyItemRangeInserted(0, comments.size());
        } else {
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mCommentList.size();
                }

                @Override
                public int getNewListSize() {
                    return comments.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    Comment old = mCommentList.get(oldItemPosition);
                    Comment comment = comments.get(newItemPosition);
                    return old.getId() == comment.getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Comment old = mCommentList.get(oldItemPosition);
                    Comment comment = comments.get(newItemPosition);
                    return old.getId() == comment.getId()
                            && old.getPostedAt() == comment.getPostedAt()
                            && old.getProductId() == comment.getProductId()
                            && TextUtils.equals(old.getText(), comment.getText());
                }
            });
            mCommentList = comments;
            diffResult.dispatchUpdatesTo(this);
        }
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CommentItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.comment_item,
                        parent, false);
        binding.setCallback(mCommentClickCallback);
        return new CommentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        holder.binding.setComment(mCommentList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mCommentList == null ? 0 : mCommentList.size();
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder {
        final CommentItemBinding binding;

        public CommentViewHolder(CommentItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
