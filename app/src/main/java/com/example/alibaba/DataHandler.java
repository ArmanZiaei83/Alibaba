package com.example.alibaba;

import android.content.Context;
import android.provider.ContactsContract;

import androidx.room.Room;

import com.example.alibaba.DataBase.DataBase;
import com.example.alibaba.DataBase.RepoEntity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class DataHandler {

    DataBase dataBase;
    Context context;

    public DataHandler(Context context) {
        this.context = context;
        dataBase = Room.databaseBuilder(context , DataBase.class , "repoinfo.db").allowMainThreadQueries().build();
    }

    public DataBase getDataBase() {
        return dataBase;
    }
    public void addToDb(int id , int forks , int stars , String desc , String collaborators , String userOwner,String url ){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                dataBase.repoDao().insertData(new RepoEntity(id , forks , stars , desc , collaborators , userOwner , url));
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        makeSout("Added");
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        System.out.println("Error : " + e.getMessage());
                    }
                });
    }

    public void deleteData(int id){

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                dataBase.repoDao().deleteDataById(id);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        makeSout("<< Data Deleted >>");
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        makeSout("Error in deleting data : " + e.getMessage());
                    }
                });
    }
    public void makeSout(String message){
        System.out.println(message);
    }
}
