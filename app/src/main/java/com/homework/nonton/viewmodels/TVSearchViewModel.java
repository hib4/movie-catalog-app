package com.homework.nonton.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.homework.nonton.repositories.TVSearchRepository;
import com.homework.nonton.responses.TVResponse;

public class TVSearchViewModel extends ViewModel {

    private TVSearchRepository tvSearchRepository;

    public TVSearchViewModel() {
        tvSearchRepository = new TVSearchRepository();
    }

    public LiveData<TVResponse> searchTV(String API_KEY, int PAGE, String QUERY) {
        return tvSearchRepository.searchTV(API_KEY, PAGE, QUERY);
    }

}
