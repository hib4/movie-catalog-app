package com.homework.moviecatalog.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.homework.moviecatalog.repositories.TVDetailsRepository;
import com.homework.moviecatalog.responses.TVDetailsResponse;

public class TVDetailsViewModel extends ViewModel {

    private TVDetailsRepository tvDetailsRepository;

    public TVDetailsViewModel() {
        tvDetailsRepository = new TVDetailsRepository();
    }

    public LiveData<TVDetailsResponse> getPopularTVDetails(String ID, String API_KEY) {
        return tvDetailsRepository.getPopularTVDetails(ID, API_KEY);
    }

}
