package com.example.alibaba.DataBase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface RepoDao {

    @Query("SELECT * FROM repos_info")
    Flowable<List<RepoEntity>> getAllData();

    @Query("DELETE FROM repos_info WHERE id Like :id")
    void deleteDataById(int id);

    @Insert
    void insertData(RepoEntity repoEntity);

    @Query("SELECT EXISTS(SELECT * FROM repos_info WHERE id = :id)")
    boolean isExist(int id);
}

