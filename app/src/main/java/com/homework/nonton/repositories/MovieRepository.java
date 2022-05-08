package com.homework.nonton.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.homework.nonton.network.ApiClient;
import com.homework.nonton.network.ApiService;
import com.homework.nonton.responses.MovieResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {

    private ApiService apiService;

    public MovieRepository() {
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<MovieResponse> getPopularMovie(String API_KEY, int PAGE) {
        MutableLiveData<MovieResponse> data = new MutableLiveData<>();
        apiService.getMoviePopular(API_KEY, PAGE).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call,@NonNull Response<MovieResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call,@NonNull Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
