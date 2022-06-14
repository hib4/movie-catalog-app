package com.homework.moviecatalog.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.homework.moviecatalog.repositories.TVRepository;
import com.homework.moviecatalog.responses.TVResponse;

public class TVViewModel extends ViewModel {

    private TVRepository tvRepository;

    public TVViewModel() {
        tvRepository = new TVRepository();
    }

    public LiveData<TVResponse> getPopularTVShows(String API_KEY, int PAGE) {
        return tvRepository.getPopularTVShows(API_KEY, PAGE);
    }
}
