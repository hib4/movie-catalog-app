package com.homework.nonton.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.homework.nonton.repositories.TVDetailsRepository;
import com.homework.nonton.responses.TVDetailsResponse;

public class TVDetailsViewModel extends ViewModel {

    private TVDetailsRepository tvDetailsRepository;

    public TVDetailsViewModel() {
        tvDetailsRepository = new TVDetailsRepository();
    }

    public LiveData<TVDetailsResponse> getPopularTVDetails(String ID, String API_KEY) {
        return tvDetailsRepository.getPopularTVDetails(ID, API_KEY);
    }

}
