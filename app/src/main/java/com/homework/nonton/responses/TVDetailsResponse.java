package com.homework.nonton.responses;

import com.google.gson.annotations.SerializedName;
import com.homework.nonton.models.GenresItemTV;

import java.util.List;

public class TVDetailsResponse {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("original_language")
    private String originalLanguage;

    @SerializedName("number_of_episodes")
    private int numberOfEpisodes;

    @SerializedName("type")
    private String type;

    @SerializedName("genres")
    private List<GenresItemTV> genres;

    @SerializedName("popularity")
    private double popularity;

    @SerializedName("number_of_seasons")
    private int numberOfSeasons;

    @SerializedName("vote_count")
    private int voteCount;

    @SerializedName("first_air_date")
    private String firstAirDate;

    @SerializedName("overview")
    private String overview;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("original_name")
    private String originalName;

    @SerializedName("vote_average")
    private String voteAverage;

    @SerializedName("tagline")
    private String tagline;

    @SerializedName("adult")
    private boolean adult;

    @SerializedName("last_air_date")
    private String lastAirDate;

    @SerializedName("homepage")
    private String homepage;

    @SerializedName("status")
    private String status;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public int getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    public String getType() {
        return type;
    }

    public List<GenresItemTV> getGenres() {
        return genres;
    }

    public double getPopularity() {
        return popularity;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOriginalName() {
        return originalName;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public String getTagline() {
        return tagline;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getLastAirDate() {
        return lastAirDate;
    }

    public String getHomepage() {
        return homepage;
    }

    public String getStatus() {
        return status;
    }

}
