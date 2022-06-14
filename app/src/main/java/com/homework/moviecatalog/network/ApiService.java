package com.homework.moviecatalog.network;

import com.homework.moviecatalog.responses.MovieDetailsResponse;
import com.homework.moviecatalog.responses.MovieResponse;
import com.homework.moviecatalog.responses.TVDetailsResponse;
import com.homework.moviecatalog.responses.TVResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("tv/popular")
    Call<TVResponse> getTVPopular(@Query("api_key") String API_KEY, @Query("page") int PAGE);

    @GET("tv/{id}")
    Call<TVDetailsResponse> getTVPopularDetails(@Path("id") String ID, @Query("api_key") String API_KEY);

    @GET("movie/popular")
    Call<MovieResponse> getMoviePopular(@Query("api_key") String API_KEY, @Query("page") int PAGE);

    @GET("movie/{id}")
    Call<MovieDetailsResponse> getMoviePopularDetails(@Path("id") String ID, @Query("api_key") String API_KEY);

    @GET("search/tv")
    Call<TVResponse> searchTV(@Query("api_key") String API_KEY, @Query("page") int PAGE, @Query("query") String QUERY);

    @GET("search/movie")
    Call<MovieResponse> searchMovie(@Query("api_key") String API_KEY, @Query("query") String QUERY, @Query("page") int PAGE);

}
