package com.example.alibaba.Models;

import com.google.gson.annotations.SerializedName;

public class Repo {

    @SerializedName("id")
    int id;
    @SerializedName("name")
    String name;
    @SerializedName("full_name")
    String full_name;

    public Repo(int id, String name, String fulllName) {
        this.id = id;
        this.name = name;
        this.full_name = fulllName;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }
}
