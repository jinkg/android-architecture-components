package com.kinglloy.example.android.persistence;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.kinglloy.example.android.persistence.db.AppDatabase;
import com.kinglloy.example.android.persistence.db.entity.CommentEntity;
import com.kinglloy.example.android.persistence.db.entity.ProductEntity;

import java.util.List;

/**
 * Repository handling the work with products and comments.
 *
 * @author jinyalin
 * @since 2018/1/19.
 */

public class DataRepository {
    private static DataRepository sInstance;

    private final AppDatabase mDatabase;
    private MediatorLiveData<List<ProductEntity>> mObservableProducts;

    public DataRepository(final AppDatabase database) {
        mDatabase = database;
        mObservableProducts = new MediatorLiveData<>();

        mObservableProducts.addSource(mDatabase.productDao().loadAllProducts(),
                productEntities -> {
                    if (mDatabase.getDatabaseCreated().getValue() != null) {
                        mObservableProducts.postValue(productEntities);
                    }
                });
    }

    public static DataRepository getInstance(final AppDatabase database) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database);
                }
            }
        }
        return sInstance;
    }

    /**
     * Get the list of products from the database and get notified when the data changes.
     */
    public LiveData<List<ProductEntity>> getProducts() {
        return mObservableProducts;
    }

    public LiveData<ProductEntity> loadProduct(final int productId) {
        return mDatabase.productDao().loadProduct(productId);
    }

    public LiveData<List<CommentEntity>> loadComments(final int productId) {
        return mDatabase.commentDao().loadComments(productId);
    }
}
