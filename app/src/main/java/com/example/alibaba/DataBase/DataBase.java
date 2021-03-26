package com.example.alibaba.DataBase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {RepoEntity.class} , version = 1)
public abstract class DataBase extends RoomDatabase {

    public abstract RepoDao repoDao();
}
