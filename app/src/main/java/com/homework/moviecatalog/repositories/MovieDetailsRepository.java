package com.homework.moviecatalog.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.homework.moviecatalog.network.ApiClient;
import com.homework.moviecatalog.network.ApiService;
import com.homework.moviecatalog.responses.MovieDetailsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsRepository {

    private ApiService apiService;

    public MovieDetailsRepository() {
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<MovieDetailsResponse> getPopularMovieDetails(String ID, String API_KEY) {
        MutableLiveData<MovieDetailsResponse> data = new MutableLiveData<>();
        apiService.getMoviePopularDetails(ID, API_KEY).enqueue(new Callback<MovieDetailsResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieDetailsResponse> call,@NonNull Response<MovieDetailsResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<MovieDetailsResponse> call,@NonNull Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

}
