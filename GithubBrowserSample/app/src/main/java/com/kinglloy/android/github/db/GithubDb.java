package com.kinglloy.android.github.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.kinglloy.android.github.vo.Contributor;
import com.kinglloy.android.github.vo.Repo;
import com.kinglloy.android.github.vo.RepoSearchResult;
import com.kinglloy.android.github.vo.User;

/**
 * Main database description.
 *
 * @author jinyalin
 * @since 2018/2/9.
 */
@Database(entities = {User.class, Repo.class, Contributor.class,
        RepoSearchResult.class}, version = 3)
public abstract class GithubDb extends RoomDatabase {
    abstract public UserDao userDao();

    abstract public RepoDao repoDao();
}
