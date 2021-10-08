package com.example.menukorvet.api;

import com.example.menukorvet.pojo.Menu;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("driver.php")
    Observable<Menu> getCanteen(@Query("_link") String link, @Query("_exec") String exec, @Query("_short") String type);
}
