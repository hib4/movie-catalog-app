package com.homework.nonton.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.homework.nonton.network.ApiClient;
import com.homework.nonton.network.ApiService;
import com.homework.nonton.responses.TVResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TVRepository {

    private ApiService apiService;

    public TVRepository() {
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<TVResponse> getPopularTVShows(String API_KEY, int PAGE) {
        MutableLiveData<TVResponse> data = new MutableLiveData<>();
        apiService.getTVPopular(API_KEY, PAGE).enqueue(new Callback<TVResponse>() {
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
