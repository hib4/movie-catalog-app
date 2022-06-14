package com.homework.moviecatalog.models;

import androidx.room.Entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "tv")
public class TVModel implements Serializable {

	@SerializedName("first_air_date")
	private String firstAirDate;

	@SerializedName("overview")
	private String overview;

	@SerializedName("original_language")
	private String originalLanguage;

	@SerializedName("genre_ids")
	private List<Integer> genreIds;

	@SerializedName("poster_path")
	private String posterPath;

	@SerializedName("origin_country")
	private List<String> originCountry;

	@SerializedName("backdrop_path")
	private String backdropPath;

	@SerializedName("original_name")
	private String originalName;

	@SerializedName("popularity")
	private double popularity;

	@SerializedName("vote_average")
	private double voteAverage;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("vote_count")
	private int voteCount;

	public String getFirstAirDate() {
		return firstAirDate;
	}

	public String getOverview() {
		return overview;
	}

	public String getOriginalLanguage() {
		return originalLanguage;
	}

	public List<Integer> getGenreIds() {
		return genreIds;
	}

	public String getPosterPath() {
		return posterPath;
	}

	public List<String> getOriginCountry() {
		return originCountry;
	}

	public String getBackdropPath() {
		return backdropPath;
	}

	public String getOriginalName() {
		return originalName;
	}

	public double getPopularity() {
		return popularity;
	}

	public double getVoteAverage() {
		return voteAverage;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public int getVoteCount() {
		return voteCount;
	}

}