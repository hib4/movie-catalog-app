package com.homework.nonton.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.homework.nonton.models.MovieModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@SuppressWarnings("ALL")
@Dao
public interface MovieDao {

    @Query("SELECT * FROM movie")
    Flowable<List<MovieModel>> getFavourite();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable addToFavourite(MovieModel movieModel);

    @Delete
    Completable removeFromFavourite(MovieModel movieModel);

    @Query("SELECT * FROM movie WHERE id = :movieId")
    Flowable<MovieModel> getMovieFromFavourite(String movieId);

}
