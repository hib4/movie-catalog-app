package com.homework.nonton.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.homework.nonton.database.MovieDatabase;
import com.homework.nonton.models.MovieModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class FavouriteViewModel extends AndroidViewModel {

    private MovieDatabase movieDatabase;

    public FavouriteViewModel(@NonNull Application application) {
        super(application);
        movieDatabase = MovieDatabase.getMovieDatabase(application);
    }

    public Flowable<List<MovieModel>> loadFavourite() {
        return movieDatabase.movieDao().getFavourite();
    }

    public Completable removeMovieFromFavourite(MovieModel movieModel) {
        return movieDatabase.movieDao().removeFromFavourite(movieModel);
    }

}
