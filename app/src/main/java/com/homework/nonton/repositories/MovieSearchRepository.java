package com.homework.nonton.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.homework.nonton.network.ApiClient;
import com.homework.nonton.network.ApiService;
import com.homework.nonton.responses.MovieResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieSearchRepository {

    private ApiService apiService;

    public MovieSearchRepository() {
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public MutableLiveData<MovieResponse> searchMovie(String API_KEY, String QUERY, int PAGE) {
        MutableLiveData<MovieResponse> data = new MutableLiveData<>();
        apiService.searchMovie(API_KEY, QUERY, PAGE).enqueue(new Callback<MovieResponse>() {
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
