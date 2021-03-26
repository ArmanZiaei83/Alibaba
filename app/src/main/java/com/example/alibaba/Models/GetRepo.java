package com.example.alibaba.Models;

import com.google.gson.annotations.SerializedName;

public class GetRepo {

    @SerializedName("stargazers_count")
    int stargazers_count;

    @SerializedName("forks_count")
    int forks_count;

    @SerializedName("description")
    String description;

    @SerializedName("collaborators_url")
    String collaborators_url;

    @SerializedName("html_url")
    String html_url;

    @SerializedName("full_name")
    String full_name;

    @SerializedName("id")
    int id;

    public GetRepo(int stargazers_count, int forks_count, String description, String collaborators_url, String html_url, String full_name, int id) {
        this.stargazers_count = stargazers_count;
        this.forks_count = forks_count;
        this.description = description;
        this.collaborators_url = collaborators_url;
        this.html_url = html_url;
        this.full_name = full_name;
        this.id = id;
    }

    public int getStargazers_count() {
        return stargazers_count;
    }

    public void setStargazers_count(int stargazers_count) {
        this.stargazers_count = stargazers_count;
    }

    public int getForks_count() {
        return forks_count;
    }

    public void setForks_count(int forks_count) {
        this.forks_count = forks_count;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCollaborators_url() {
        return collaborators_url;
    }

    public void setCollaborators_url(String collaborators_url) {
        this.collaborators_url = collaborators_url;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "GetRepo{" +
                "stargazers_count=" + stargazers_count +
                ", forks_count=" + forks_count +
                ", description='" + description + '\'' +
                ", collaborators_url='" + collaborators_url + '\'' +
                ", html_url='" + html_url + '\'' +
                ", full_name='" + full_name + '\'' +
                '}';
    }
}
