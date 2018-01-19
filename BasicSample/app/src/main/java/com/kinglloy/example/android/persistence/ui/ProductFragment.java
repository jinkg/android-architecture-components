package com.kinglloy.example.android.persistence.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kinglloy.example.android.persistence.R;
import com.kinglloy.example.android.persistence.databinding.ProductFragmentBinding;
import com.kinglloy.example.android.persistence.viewmodel.ProductViewModel;

/**
 * @author jinyalin
 * @since 2018/1/19.
 */

public class ProductFragment extends Fragment {
    private static final String KEY_PRODUCT_ID = "product_id";

    private ProductFragmentBinding mBinding;

    private CommentAdapter mCommentAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.product_fragment, container, false);

        mCommentAdapter = new CommentAdapter(mCommentClickCallback);
        mBinding.commentList.setAdapter(mCommentAdapter);

        return mBinding.getRoot();
    }

    private final CommentClickCallback mCommentClickCallback = comment -> {

    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ProductViewModel.Factory factory = new ProductViewModel.Factory(
                getActivity().getApplication(), getArguments().getInt(KEY_PRODUCT_ID));

        final ProductViewModel model = ViewModelProviders.of(this, factory)
                .get(ProductViewModel.class);

        mBinding.setProductViewModel(model);

        subscribeToModel(model);
    }

    private void subscribeToModel(final ProductViewModel model) {
        model.getObservableProduct().observe(this, model::setProduct);

        model.getComments().observe(this, commentEntities -> {
            if (commentEntities != null) {
                mBinding.setIsLoading(false);
                mCommentAdapter.setCommentList(commentEntities);
            } else {
                mBinding.setIsLoading(true);
            }
        });
    }

    /**
     * Creates product fragment for specific product ID
     */
    public static ProductFragment forProduct(int productId) {
        ProductFragment fragment = new ProductFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_PRODUCT_ID, productId);
        fragment.setArguments(args);
        return fragment;
    }
}
