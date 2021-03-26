package com.example.alibaba.DataBase;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "repos_info")
public class RepoEntity {

    @PrimaryKey
    int id;

    @ColumnInfo(name = "forks")
    int forks;

    @ColumnInfo(name = "stars")
    int stars;

    @ColumnInfo(name = "description")
    String description;

    @ColumnInfo(name = "collaborators")
    String collaborators;

    @ColumnInfo(name = "userOwner")
    String userOwner;

    @ColumnInfo(name = "url")
    String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getForks() {
        return forks;
    }

    public void setForks(int forks) {
        this.forks = forks;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCollaborators() {
        return collaborators;
    }

    public void setCollaborators(String collaborators) {
        this.collaborators = collaborators;
    }

    public String getUserOwner() {
        return userOwner;
    }

    public void setUserOwner(String userOwner) {
        this.userOwner = userOwner;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public RepoEntity(int id, int forks, int stars, String description, String collaborators, String userOwner, String url) {
        this.id = id;
        this.forks = forks;
        this.stars = stars;
        this.description = description;
        this.collaborators = collaborators;
        this.userOwner = userOwner;
        this.url = url;
    }
}
