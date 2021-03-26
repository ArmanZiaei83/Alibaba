package com.example.alibaba.Retrofit;

import com.example.alibaba.Models.GetRepo;
import com.example.alibaba.Models.Repo;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Api {

    @GET("repositories")
    Observable<List<Repo>> getRepos();

    @GET("repos/{owner}/{repo}")
    Observable<GetRepo> getRepsDetail(@Path("owner") String owner , @Path("repo") String repo) ;
}
