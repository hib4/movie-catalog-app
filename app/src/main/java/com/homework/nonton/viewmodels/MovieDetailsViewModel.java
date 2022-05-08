package com.homework.nonton.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.homework.nonton.repositories.MovieDetailsRepository;
import com.homework.nonton.responses.MovieDetailsResponse;

public class MovieDetailsViewModel extends ViewModel {

    private MovieDetailsRepository movieDetailsRepository;

    public MovieDetailsViewModel() {
        movieDetailsRepository = new MovieDetailsRepository();
    }

    public LiveData<MovieDetailsResponse> getPopularMovieDetails(String ID, String API_KEY) {
        return movieDetailsRepository.getPopularMovieDetails(ID, API_KEY);
    }

}
