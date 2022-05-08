package com.homework.nonton.models;

import com.google.gson.annotations.SerializedName;

public class GenresItemTV {

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	public String getName(){
		return name;
	}

	public int getId(){
		return id;
	}

}