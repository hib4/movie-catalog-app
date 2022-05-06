package com.homework.nonton.responses;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.homework.nonton.models.TVModel;

public class TVResponse {

	@SerializedName("page")
	private int page;

	@SerializedName("total_pages")
	private int totalPages;

	@SerializedName("results")
	private List<TVModel> results;

	@SerializedName("total_results")
	private int totalResults;

	public int getPage() {
		return page;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public List<TVModel> getResults() {
		return results;
	}

	public int getTotalResults() {
		return totalResults;
	}

}