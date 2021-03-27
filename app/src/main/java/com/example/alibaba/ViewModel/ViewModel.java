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

    DataBase dataBase;
    Context context;

    public Observable<Repo> getRepo () {
        return new RetrofitHolder().getApi()
                .getRepos()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<List<Repo>, ObservableSource<Repo>>() {
                    @Override
                    public ObservableSource<Repo> apply(@NotNull List<Repo> repos) throws Exception {
                        return Observable.fromIterable(repos).subscribeOn(Schedulers.io());
                    }
                });
    }

    public Observable<GetRepo> getRepoObservable(String owner , String repo){
        return new RetrofitHolder().getApi()
                .getRepsDetail(owner , repo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    //Repo Model Subscriber :

    public void repoSubscriber(Observable<Repo> repoObservable){
        repoObservable.subscribe(new Observer<Repo>() {
            @Override
            public void onSubscribe(@NotNull Disposable d) {
                System.out.println("<< Started >>" + "\n" );
            }

            @Override
            public void onNext(@NotNull Repo repo) {
                System.out.println("repo info : " + repo.getId());
                detailSubscriber(getRepoObservable(repo.getFull_name().replace("/" + repo.getName(), "")
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


    //Second Link Subscriber :
    public void detailSubscriber(Observable<GetRepo> getRepoObservable){
        getRepoObservable.subscribe(new Observer<GetRepo>() {
            @Override
            public void onSubscribe(@NotNull Disposable d) {
                makeSout("<< Detail Started >>");
            }

            @Override
            public void onNext(@NotNull GetRepo getRepo) {
                if(dataBase.repoDao().isExist(getRepo.getId())){
                    deleteData(getRepo.getId());
                    addToDb(getRepo.getId(), getRepo.getForks_count() , getRepo.getStargazers_count() , getRepo.getDescription() ,
                            getRepo.getCollaborators_url(), getRepo.getFull_name() , getRepo.getHtml_url());

                }else {
                    addToDb(getRepo.getId(), getRepo.getForks_count() , getRepo.getStargazers_count() , getRepo.getDescription() ,
                            getRepo.getCollaborators_url(), getRepo.getFull_name() , getRepo.getHtml_url());
                }
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


    //Room insertor :

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
                        makeSout("Deleting Process Finished");
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {

                    }
                });
    }

    public DataBase getDataBase() {
        return dataBase;
    }
    public void makeSout(String message){
        System.out.println(message);
    }

    public void prepareRepo(){
        repoSubscriber(getRepo());
    }

    public void createRoomDb(Context context){
        this.context = context;
        dataBase = Room.databaseBuilder(context , DataBase.class , "repo_info.db").allowMainThreadQueries().build();
    }

}
