package com.kinglloy.example.android.persistence.db;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.kinglloy.example.android.persistence.LiveDataTestUtil;
import com.kinglloy.example.android.persistence.db.dao.ProductDao;
import com.kinglloy.example.android.persistence.db.entity.ProductEntity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static com.kinglloy.example.android.persistence.db.TestData.PRODUCTS;
import static com.kinglloy.example.android.persistence.db.TestData.PRODUCT_ENTITY;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Test the implementation of {@link ProductDao}
 *
 * @author jinyalin
 * @since 2018/1/21.
 */
@RunWith(AndroidJUnit4.class)
public class ProductDaoTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private AppDatabase mDatabase;

    private ProductDao mProductDao;

    @Before
    public void initDb() throws Exception {
        // using an in-memory database because the information stored here disappears when the
        // process is killed
        mDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                AppDatabase.class)
                // allowing main thread queries, just for testing
                .allowMainThreadQueries()
                .build();

        mProductDao = mDatabase.productDao();
    }

    @After
    public void closeDb() throws Exception {
        mDatabase.close();
    }

    @Test
    public void getProductsWhenNoProductInserted() throws InterruptedException {
        List<ProductEntity> products = LiveDataTestUtil.getValue(mProductDao.loadAllProducts());

        assertTrue(products.isEmpty());
    }

    @Test
    public void getProductsAfterInserted() throws InterruptedException {
        mProductDao.insertAll(PRODUCTS);

        List<ProductEntity> products = LiveDataTestUtil.getValue(mProductDao.loadAllProducts());

        assertThat(products.size(), is(PRODUCTS.size()));
    }

    @Test
    public void getProductById() throws InterruptedException {
        mProductDao.insertAll(PRODUCTS);

        ProductEntity product = LiveDataTestUtil.getValue(mProductDao.loadProduct(
                PRODUCT_ENTITY.getId()));

        assertThat(product.getId(), is(PRODUCT_ENTITY.getId()));
        assertThat(product.getName(), is(PRODUCT_ENTITY.getName()));
        assertThat(product.getDescription(), is(PRODUCT_ENTITY.getDescription()));
        assertThat(product.getPrice(), is(PRODUCT_ENTITY.getPrice()));
    }
}
