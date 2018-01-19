package com.kinglloy.example.android.persistence.db;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.database.sqlite.SQLiteConstraintException;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.kinglloy.example.android.persistence.LiveDataTestUtil;
import com.kinglloy.example.android.persistence.db.dao.CommentDao;
import com.kinglloy.example.android.persistence.db.dao.ProductDao;
import com.kinglloy.example.android.persistence.db.entity.CommentEntity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static com.kinglloy.example.android.persistence.db.TestData.COMMENT_ENTITY;
import static com.kinglloy.example.android.persistence.db.TestData.COMMENTS;
import static com.kinglloy.example.android.persistence.db.TestData.PRODUCTS;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author jinyalin
 * @since 2018/1/19.
 */
@RunWith(AndroidJUnit4.class)
public class CommentDaoTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private AppDatabase mDatabase;

    private CommentDao mCommentDao;

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

        mCommentDao = mDatabase.commentDao();
        mProductDao = mDatabase.productDao();
    }

    @After
    public void closeDb() throws Exception {
        mDatabase.close();
    }

    @Test
    public void getCommentsWhenNoCommentInserted() throws InterruptedException {
        List<CommentEntity> comments = LiveDataTestUtil.getValue(mCommentDao.loadComments(
                COMMENT_ENTITY.getProductId()));

        assertTrue(comments.isEmpty());
    }

    @Test
    public void canInsertCommentWithoutProduct() throws InterruptedException {
        try {
            mCommentDao.insertAll(COMMENTS);
            fail("SQLiteConstraintException expected");
        } catch (SQLiteConstraintException ignored) {

        }
    }

    @Test
    public void getCommentsAfterInserted() throws InterruptedException {
        mProductDao.insertAll(PRODUCTS);
        mCommentDao.insertAll(COMMENTS);

        List<CommentEntity> comments = LiveDataTestUtil.getValue(mCommentDao.loadComments(
                COMMENT_ENTITY.getProductId()));

        assertThat(comments.size(), is(1));
    }

    @Test
    public void getCommentByProductId() throws InterruptedException {
        mProductDao.insertAll(PRODUCTS);
        mCommentDao.insertAll(COMMENTS);

        List<CommentEntity> comments = LiveDataTestUtil.getValue(mCommentDao.loadComments(
                COMMENT_ENTITY.getProductId()));

        assertThat(comments.size(), is(1));
    }
}
