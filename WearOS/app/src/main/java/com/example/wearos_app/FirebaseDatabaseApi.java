package com.example.wearos_app;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface FirebaseDatabaseApi {
    @POST("data.json/")
    Call<DataSnap> sendDataSnap(@Body DataSnap ds);
}
