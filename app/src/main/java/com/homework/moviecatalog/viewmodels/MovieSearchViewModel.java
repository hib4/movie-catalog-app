package com.homework.moviecatalog.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.homework.moviecatalog.repositories.MovieSearchRepository;
import com.homework.moviecatalog.responses.MovieResponse;

public class MovieSearchViewModel extends ViewModel {

    private MovieSearchRepository movieSearchRepository;

    public MovieSearchViewModel() {
        movieSearchRepository = new MovieSearchRepository();
    }

    public LiveData<MovieResponse> searchMovie(String API_KEY, String QUERY, int PAGE) {
        return movieSearchRepository.searchMovie(API_KEY, QUERY, PAGE);
    }

}
