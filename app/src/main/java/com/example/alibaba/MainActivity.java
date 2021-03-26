package com.example.alibaba;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.alibaba.Adapters.Adapter;
import com.example.alibaba.DataBase.RepoEntity;
import com.example.alibaba.Models.Repo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SetOnClick {

    List<RepoEntity> entities = new ArrayList<>();

    Adapter adapter = new Adapter(this);
    RecyclerView recyclerView;
    RecyclerView.LayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataHandler handler = new DataHandler();

        handler.createDataBase(this);
        handler.getAllData(entities);

        initRecyclerView();

    }


    @Override
    public void onClick(int position) {
        entities.get(position);

        RepoEntity entity = entities.get(position);

        Intent intent = new Intent(this , UserInfo.class);

        intent.putExtra("stars" , entity.getStars());
        intent.putExtra("forks" , entity.getForks());
        intent.putExtra("desc" , entity.getDescription());
        intent.putExtra("coll" , entity.getCollaborators());
        intent.putExtra("url" , entity.getUrl());
        intent.putExtra("userOwner" , entity.getUserOwner());

        startActivity(intent);

    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        manager = new LinearLayoutManager(this);

        adapter.setEntities(entities);
        recyclerView.setAdapter(adapter);
    }
}