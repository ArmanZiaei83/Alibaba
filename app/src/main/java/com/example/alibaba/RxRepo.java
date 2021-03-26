package com.example.alibaba;

import com.example.alibaba.Models.Repo;
import com.example.alibaba.Retrofit.RetrofitHolder;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxRepo {

    public Observable<Repo> getRepo(){
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

}
