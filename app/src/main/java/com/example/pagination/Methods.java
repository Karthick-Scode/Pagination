package com.example.pagination;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Methods {
    @GET("api/users")
    Call<Model> getAllData(@Query("page") int pageNo);
}
