package com.homework.nonton.ui.listeners;

import com.homework.nonton.models.MovieModel;

public interface FavouriteListener {

    void onMovieClicked(MovieModel movieModel);

    void removeMovieFromFavourite(MovieModel movieModel, int position);

}
