package com.homework.moviecatalog.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.homework.moviecatalog.network.ApiClient;
import com.homework.moviecatalog.network.ApiService;
import com.homework.moviecatalog.responses.TVResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TVSearchRepository {

    private ApiService apiService;

    public TVSearchRepository() {
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<TVResponse> searchTV(String API_KEY, int PAGE, String QUERY) {
        MutableLiveData<TVResponse> data = new MutableLiveData<>();
        apiService.searchTV(API_KEY, PAGE, QUERY).enqueue(new Callback<TVResponse>() {
            @Override
            public void onResponse(@NonNull Call<TVResponse> call,@NonNull Response<TVResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<TVResponse> call,@NonNull Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

}
