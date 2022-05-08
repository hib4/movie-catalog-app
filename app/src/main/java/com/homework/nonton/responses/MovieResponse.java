package com.homework.nonton.responses;

import com.google.gson.annotations.SerializedName;
import com.homework.nonton.models.MovieModel;

import java.util.List;

public class MovieResponse{

	@SerializedName("page")
	private int page;

	@SerializedName("total_pages")
	private int totalPages;

	@SerializedName("results")
	private List<MovieModel> results;

	@SerializedName("total_results")
	private int totalResults;

	public int getPage(){
		return page;
	}

	public int getTotalPages(){
		return totalPages;
	}

	public List<MovieModel> getResults(){
		return results;
	}

	public int getTotalResults(){
		return totalResults;
	}
}