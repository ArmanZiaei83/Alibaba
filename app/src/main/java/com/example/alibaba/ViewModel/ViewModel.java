package com.example.alibaba.ViewModel;

import android.content.Context;

import androidx.room.Room;

import com.example.alibaba.DataBase.DataBase;
import com.example.alibaba.DataBase.RepoEntity;
import com.example.alibaba.DataHandler;
import com.example.alibaba.Models.GetRepo;
import com.example.alibaba.Models.Repo;
import com.example.alibaba.Retrofit.RetrofitHolder;
import com.example.alibaba.RxGetRepo;
import com.example.alibaba.RxRepo;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ViewModel extends androidx.lifecycle.ViewModel {

    RxRepo rxRepo = new RxRepo();
    RxGetRepo getRepo = new RxGetRepo();

    DataHandler handler;

    public void prepareHandler(Context context){
        handler = new DataHandler(context);
    }

    public DataHandler getHandler(){
        return handler;
    }

    public void repoSubscriber(Observable<Repo> repoObservable){
        repoObservable.subscribe(new Observer<Repo>() {
            @Override
            public void onSubscribe(@NotNull Disposable d) {
                System.out.println("<< Started >>" + "\n" );
            }

            @Override
            public void onNext(@NotNull Repo repo) {
                System.out.println("repo info : " + repo.getId());
                detailSubscriber(getRepo.getRepoObservable(repo.getFull_name().replace("/" + repo.getName(), "")
                        , repo.getName()));
            }

            @Override
            public void onError(@NotNull Throwable e) {
                System.out.println("Error >>> " + e.getMessage() + "\n" );
            }

            @Override
            public void onComplete() {
                System.out.println("\n" + "<< Finished >>");
            }
        });
    }

    public void detailSubscriber(Observable<GetRepo> getRepoObservable){
        getRepoObservable.subscribe(new Observer<GetRepo>() {
            @Override
            public void onSubscribe(@NotNull Disposable d) {
                makeSout("<< Detail Started >>");
            }

            @Override
            public void onNext(@NotNull GetRepo getRepo) {
                    handler.addToDb(getRepo.getId(), getRepo.getForks_count() , getRepo.getStargazers_count() , getRepo.getDescription() ,
                            getRepo.getCollaborators_url(), getRepo.getFull_name() , getRepo.getHtml_url());
            }

            @Override
            public void onError(@NotNull Throwable e) {
                makeSout("Error >>> " + e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void lastStep(){
        repoSubscriber(rxRepo.getRepo());
    }

    public void makeSout(String message){
        System.out.println(message);
    }
}
