package com.kinglloy.android.github.vo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * @author jinyalin
 * @since 2018/2/9.
 */
@Entity(primaryKeys = {"repoName", "repoOwner", "login"},
        foreignKeys = @ForeignKey(entity = Repo.class,
                parentColumns = {"name", "owner_login"},
                childColumns = {"repoName", "repoOwner"},
                onUpdate = ForeignKey.CASCADE,
                deferred = true))
public class Contributor {
    @SerializedName("login")
    @NonNull
    private final String login;

    @SerializedName("contributions")
    private final int contributions;

    @SerializedName("avatar_url")
    private final String avatarUrl;

    @NonNull
    private String repoName;

    @NonNull
    private String repoOwner;

    public Contributor(@NonNull String login, int contributions, String avatarUrl) {
        this.login = login;
        this.contributions = contributions;
        this.avatarUrl = avatarUrl;
    }


    public void setRepoName(@NonNull String repoName) {
        this.repoName = repoName;
    }

    public void setRepoOwner(@NonNull String repoOwner) {
        this.repoOwner = repoOwner;
    }

    public String getLogin() {
        return login;
    }

    public int getContributions() {
        return contributions;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getRepoName() {
        return repoName;
    }

    public String getRepoOwner() {
        return repoOwner;
    }
}
