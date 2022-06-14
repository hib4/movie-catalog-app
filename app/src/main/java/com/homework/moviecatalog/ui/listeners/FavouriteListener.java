package com.homework.moviecatalog.ui.listeners;

import com.homework.moviecatalog.models.MovieModel;

public interface FavouriteListener {

    void onMovieClicked(MovieModel movieModel);

    void removeMovieFromFavourite(MovieModel movieModel, int position);

}
