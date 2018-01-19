package com.kinglloy.example.android.persistence.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.kinglloy.example.android.persistence.db.entity.CommentEntity;

import java.util.List;

/**
 * @author jinyalin
 * @since 2018/1/19.
 */

@Dao
public interface CommentDao {
    @Query("SELECT * FROM comments where productId =:productId")
    LiveData<List<CommentEntity>> loadComments(int productId);

    @Query("SELECT * FROM comments where productId =:productId")
    List<CommentEntity> loadCommentsSync(int productId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CommentEntity> products);
}
