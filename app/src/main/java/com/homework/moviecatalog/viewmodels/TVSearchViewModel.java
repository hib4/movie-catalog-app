package com.homework.moviecatalog.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.homework.moviecatalog.repositories.TVSearchRepository;
import com.homework.moviecatalog.responses.TVResponse;

public class TVSearchViewModel extends ViewModel {

    private TVSearchRepository tvSearchRepository;

    public TVSearchViewModel() {
        tvSearchRepository = new TVSearchRepository();
    }

    public LiveData<TVResponse> searchTV(String API_KEY, int PAGE, String QUERY) {
        return tvSearchRepository.searchTV(API_KEY, PAGE, QUERY);
    }

}
