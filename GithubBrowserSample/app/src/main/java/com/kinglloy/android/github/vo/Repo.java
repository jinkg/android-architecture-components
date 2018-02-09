package com.kinglloy.android.github.vo;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Using name/owner_login as primary key instead of id since name/owner_login is always available
 * vs id is not.
 *
 * @author jinyalin
 * @since 2018/2/9.
 */
@Entity(indices = {@Index("id"), @Index("owner_login")},
        primaryKeys = {"name", "owner_login"})
public class Repo {
    public static final int UNKNOWN_ID = -1;
    public final int id;

    @SerializedName("name")
    @NonNull
    public final String name;
    @SerializedName("full_name")
    public final String fullName;
    @SerializedName("description")
    public final String description;
    @SerializedName("stargazers_count")
    public final int stars;
    @SerializedName("owner")
    @Embedded(prefix = "owner_")
    @NonNull
    public final Owner owner;

    public Repo(int id, @NonNull String name, String fullName, String description,
                @NonNull Owner owner, int stars) {
        this.id = id;
        this.name = name;
        this.fullName = fullName;
        this.description = description;
        this.stars = stars;
        this.owner = owner;
    }

    public static class Owner {
        @SerializedName("login")
        @NonNull
        public final String login;
        @SerializedName("url")
        public final String url;

        public Owner(@NonNull String login, String url) {
            this.login = login;
            this.url = url;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Owner owner = (Owner) obj;

            if (login != null ? !login.equals(owner.login) : owner.login != null) {
                return false;
            }
            return url != null ? url.equals(owner.url) : owner.url == null;
        }

        @Override
        public int hashCode() {
            int result = login != null ? login.hashCode() : 0;
            result = 31 * result + (url != null ? url.hashCode() : 0);
            return result;
        }
    }

}
