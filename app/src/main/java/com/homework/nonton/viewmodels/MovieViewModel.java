package com.homework.nonton.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.homework.nonton.repositories.MovieRepository;
import com.homework.nonton.responses.MovieResponse;

public class MovieViewModel extends ViewModel {

    private MovieRepository movieRepository;

    public MovieViewModel() {
        movieRepository = new MovieRepository();
    }

    public LiveData<MovieResponse> getPopularMovie(String API_KEY, int PAGE) {
        return movieRepository.getPopularMovie(API_KEY, PAGE);
    }

}
