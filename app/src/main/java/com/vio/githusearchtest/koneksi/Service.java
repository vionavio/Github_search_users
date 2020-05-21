package com.vio.githusearchtest.koneksi;

import com.vio.githusearchtest.pojo.SearchResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;


public interface Service {

   @GET("/search/users")
   Call<SearchResponse> searchUser(@Header("Authorization") String authorization,
                                   @Query("q") String username);
}
