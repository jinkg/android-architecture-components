package com.kinglloy.android.github.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.util.SparseIntArray;

import com.kinglloy.android.github.vo.Contributor;
import com.kinglloy.android.github.vo.Repo;
import com.kinglloy.android.github.vo.RepoSearchResult;

import java.util.Collections;
import java.util.List;

/**
 * Interface for database access on Repo related operations.
 *
 * @author jinyalin
 * @since 2018/2/9.
 */
@Dao
public abstract class RepoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(Repo... repos);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertContributors(List<Contributor> contributors);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertRepos(List<Repo> repositories);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long createRepoIfNotExists(Repo repo);

    @Query("SELECT * FROM repo WHERE owner_login = :login AND name= :name")
    public abstract LiveData<Repo> load(String login, String name);

    @Query("SELECT login, avatarUrl, repoName, repoOwner, contributions FROM contributor "
            + "WHERE repoName = :name AND repoOwner = :owner "
            + "ORDER BY contributions DESC")
    public abstract LiveData<List<Contributor>> loadContributors(String owner, String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(RepoSearchResult result);

    @Query("SELECT * FROM RepoSearchResult WHERE `query` = :query")
    public abstract LiveData<RepoSearchResult> search(String query);

    public LiveData<List<Repo>> loadOrdered(List<Integer> repoIds) {
        SparseIntArray order = new SparseIntArray();
        int index = 0;
        for (Integer repoId : repoIds) {
            order.put(repoId, index++);
        }
        return Transformations.map(loadById(repoIds), repositories -> {
            Collections.sort(repositories, (r1, r2) -> {
                int pos1 = order.get(r1.id);
                int pos2 = order.get(r2.id);
                return pos1 - pos2;
            });
            return repositories;
        });
    }

    @Query("SELECT * FROM repo WHERE id in (:repoIds)")
    protected abstract LiveData<List<Repo>> loadById(List<Integer> repoIds);

    @Query("SELECT * FROM RepoSearchResult WHERE `query` = :query")
    public abstract RepoSearchResult findSearchResult(String query);
}
