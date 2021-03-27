package com.example.alibaba;

import com.example.alibaba.Models.GetRepo;
import com.example.alibaba.Retrofit.RetrofitHolder;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RxGetRepo {

    public Observable<GetRepo> getRepoObservable(String owner , String repo){
        return new RetrofitHolder().getApi()
                .getRepsDetail(owner , repo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
