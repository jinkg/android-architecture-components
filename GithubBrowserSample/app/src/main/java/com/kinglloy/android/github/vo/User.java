package com.kinglloy.android.github.vo;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * @author jinyalin
 * @since 2018/2/9.
 */

@Entity(primaryKeys = "login")
public class User {
    @SerializedName("login")
    @NonNull
    public final String login;
    @SerializedName("avatar_url")
    public final String avatarUrl;
    @SerializedName("name")
    public final String name;
    @SerializedName("company")
    public final String company;
    @SerializedName("repos_url")
    public final String reposUrl;
    @SerializedName("blog")
    public final String blog;

    public User(String login, String avatarUrl, String name, String company,
                String reposUrl, String blog) {
        this.login = login;
        this.avatarUrl = avatarUrl;
        this.name = name;
        this.company = company;
        this.reposUrl = reposUrl;
        this.blog = blog;
    }
}
