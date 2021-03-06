package com.example.alibaba;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.alibaba.Adapters.Adapter;
import com.example.alibaba.DataBase.DataBase;
import com.example.alibaba.DataBase.RepoEntity;
import com.example.alibaba.Models.Repo;
import com.example.alibaba.ViewModel.ViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements SetOnClick {

    List<RepoEntity> entities = new ArrayList<>();

    Adapter adapter = new Adapter(this);
    RecyclerView recyclerView;
    RecyclerView.LayoutManager manager;

    ViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(ViewModel.class);
        viewModel.prepareHandler(this);
        viewModel.lastStep();

        getAllData();

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

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    public void getAllData(){

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                viewModel.getHandler().getDataBase().repoDao().getAllData().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<List<RepoEntity>>() {
                            @Override
                            public void accept(List<RepoEntity> repoEntities) throws Exception {
                                entities = repoEntities;
                                adapter.setEntities(repoEntities);
                                recyclerView.setAdapter(adapter);
                                for (int i = 0; i < repoEntities.size(); i++) {
                                    System.out.println("Repo Id : " + repoEntities.get(i).getId());
                                }
                            }
                        });
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        System.out.println("Got Data");
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        System.out.println("Error in getting data : " + e.getMessage());
                    }
                });
    }

    private void makeSout(String message) {
        System.out.println(message);
    }

}