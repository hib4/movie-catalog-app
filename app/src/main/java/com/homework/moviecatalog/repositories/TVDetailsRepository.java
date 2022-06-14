package com.homework.moviecatalog.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.homework.moviecatalog.network.ApiClient;
import com.homework.moviecatalog.network.ApiService;
import com.homework.moviecatalog.responses.TVDetailsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TVDetailsRepository {

    private ApiService apiService;

    public  TVDetailsRepository() {
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<TVDetailsResponse> getPopularTVDetails(String ID, String API_KEY) {
        MutableLiveData<TVDetailsResponse> data = new MutableLiveData<>();
        apiService.getTVPopularDetails(ID, API_KEY).enqueue(new Callback<TVDetailsResponse>() {
            @Override
            public void onResponse(@NonNull Call<TVDetailsResponse> call,@NonNull Response<TVDetailsResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<TVDetailsResponse> call,@NonNull Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
