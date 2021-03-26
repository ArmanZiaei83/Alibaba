package com.example.alibaba.ViewModel;

import com.example.alibaba.Models.GetRepo;
import com.example.alibaba.Models.Repo;
import com.example.alibaba.RxGetRepo;
import com.example.alibaba.RxRepo;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ViewModel extends androidx.lifecycle.ViewModel {

    RxGetRepo rxGetRepo = new RxGetRepo();

    public void repoSubscriber(Observable<Repo> repoObservable){
        repoObservable.subscribe(new Observer<Repo>() {
            @Override
            public void onSubscribe(@NotNull Disposable d) {
                System.out.println("<< Started >>" + "\n" );
            }

            @Override
            public void onNext(@NotNull Repo repo) {
                System.out.println("repo info : " + repo.getId());
                detailSubscriber(rxGetRepo.getRepoObservable(repo.getFull_name().replace("/" + repo.getName(), "")
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

    private void makeSout(String message) {
        System.out.println(message);
    }
}
