package com.kinglloy.example.android.persistence.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.kinglloy.example.android.persistence.BasicApp;
import com.kinglloy.example.android.persistence.DataRepository;
import com.kinglloy.example.android.persistence.db.entity.CommentEntity;
import com.kinglloy.example.android.persistence.db.entity.ProductEntity;

import java.util.List;

/**
 * @author jinyalin
 * @since 2018/1/19.
 */

public class ProductViewModel extends AndroidViewModel {
    private final LiveData<ProductEntity> mObservableProduct;

    public ObservableField<ProductEntity> product = new ObservableField<>();

    private final int mProductId;

    private final LiveData<List<CommentEntity>> mObservableComments;

    public ProductViewModel(@NonNull Application application, DataRepository repository,
                            final int productId) {
        super(application);
        mProductId = productId;

        mObservableComments = repository.loadComments(mProductId);
        mObservableProduct = repository.loadProduct(mProductId);
    }

    /**
     * Expose the LiveData Comments query so the UI can observe it.
     */
    public LiveData<List<CommentEntity>> getComments() {
        return mObservableComments;
    }

    public LiveData<ProductEntity> getObservableProduct() {
        return mObservableProduct;
    }

    public void setProduct(ProductEntity product) {
        this.product.set(product);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        private final Application mApplication;

        private final int mProductId;

        private final DataRepository mRepository;

        public Factory(@NonNull Application application, int productId) {
            mApplication = application;
            mProductId = productId;
            mRepository = ((BasicApp) application).getRepository();
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ProductViewModel(mApplication, mRepository, mProductId);
        }
    }
}
