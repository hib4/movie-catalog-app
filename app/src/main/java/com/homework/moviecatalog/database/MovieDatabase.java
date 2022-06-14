package com.homework.moviecatalog.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.homework.moviecatalog.dao.MovieDao;
import com.homework.moviecatalog.models.MovieModel;

@Database(entities = MovieModel.class, version = 1, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {

    private static MovieDatabase movieDatabase;

    public static synchronized MovieDatabase getMovieDatabase(Context context) {
        if (movieDatabase == null) {
            movieDatabase = Room.databaseBuilder(
                    context,
                    MovieDatabase.class,
                    "movie_db"
            ).build();
        }
        return movieDatabase;
    }

    public abstract MovieDao movieDao();

}
