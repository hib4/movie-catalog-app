package com.homework.nonton.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.homework.nonton.database.MovieDatabase;
import com.homework.nonton.models.MovieModel;
import com.homework.nonton.repositories.MovieDetailsRepository;
import com.homework.nonton.responses.MovieDetailsResponse;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class MovieDetailsViewModel extends AndroidViewModel {

    private MovieDetailsRepository movieDetailsRepository;
    private MovieDatabase movieDatabase;

    public MovieDetailsViewModel(@NonNull Application application) {
        super(application);
        movieDetailsRepository = new MovieDetailsRepository();
        movieDatabase = MovieDatabase.getMovieDatabase(application);
    }

    public LiveData<MovieDetailsResponse> getPopularMovieDetails(String ID, String API_KEY) {
        return movieDetailsRepository.getPopularMovieDetails(ID, API_KEY);
    }

    public Completable addToFavourite(MovieModel movieModel) {
        return movieDatabase.movieDao().addToFavourite(movieModel);
    }

    public Flowable<MovieModel> getMovieFromFavourite(String movieId) {
        return movieDatabase.movieDao().getMovieFromFavourite(movieId);
    }

    public Completable removeMovieFromFavourite(MovieModel movieModel) {
        return movieDatabase.movieDao().removeFromFavourite(movieModel);
    }

}
