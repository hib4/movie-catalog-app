package com.homework.nonton.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.homework.nonton.activities.MoreMovieActivity;
import com.homework.nonton.activities.MoreTVActivity;
import com.homework.nonton.activities.TVDetailsActivity;
import com.homework.nonton.adapters.MovieAdapterMain;
import com.homework.nonton.adapters.TVAdapterMain;
import com.homework.nonton.databinding.FragmentHomeBinding;
import com.homework.nonton.listeners.MovieListener;
import com.homework.nonton.listeners.TVListener;
import com.homework.nonton.models.MovieModel;
import com.homework.nonton.models.TVModel;
import com.homework.nonton.viewmodels.MovieViewModel;
import com.homework.nonton.viewmodels.TVViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements TVListener, MovieListener {

    private FragmentHomeBinding fragmentHomeBinding;

    private TVViewModel tvViewModel;
    private List<TVModel> tvModels = new ArrayList<>();
    private TVAdapterMain tvAdapterMain;

    private MovieViewModel movieViewModel;
    private List<MovieModel> movieModels = new ArrayList<>();
    private MovieAdapterMain movieAdapterMain;

    public HomeFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false);

        doInitializationTV();
        doInitializationMovie();

        return fragmentHomeBinding.getRoot();
    }

    private void doInitializationTV() {
        fragmentHomeBinding.rvListTvMain.setHasFixedSize(true);
        fragmentHomeBinding.rvListTvMain.setItemViewCacheSize(20);
        fragmentHomeBinding.rvListTvMain.setDrawingCacheEnabled(true);
        fragmentHomeBinding.rvListTvMain.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        tvViewModel = new ViewModelProvider(this).get(TVViewModel.class);
        tvAdapterMain = new TVAdapterMain(tvModels, this);
        fragmentHomeBinding.rvListTvMain.setAdapter(tvAdapterMain);
        fragmentHomeBinding.ivMoreTvMain.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), MoreTVActivity.class);
            startActivity(intent);
        });
        getPopularTV();
    }

    private void doInitializationMovie() {
        fragmentHomeBinding.rvListMovieMain.setHasFixedSize(true);
        fragmentHomeBinding.rvListMovieMain.setItemViewCacheSize(20);
        fragmentHomeBinding.rvListMovieMain.setDrawingCacheEnabled(true);
        fragmentHomeBinding.rvListMovieMain.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        movieAdapterMain = new MovieAdapterMain(movieModels, this);
        fragmentHomeBinding.rvListMovieMain.setAdapter(movieAdapterMain);
        fragmentHomeBinding.ivMoreMovieMain.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), MoreMovieActivity.class);
            startActivity(intent);
        });
        getPopularMovie();
    }

    private void getPopularTV() {
        fragmentHomeBinding.rlMain.setVisibility(View.GONE);
        fragmentHomeBinding.setIsLoading(true);
        tvViewModel.getPopularTVShows("6336e4208132f6206aa0b05d04b1fda7", 1).observe(getViewLifecycleOwner(), tvResponse -> {
            fragmentHomeBinding.rlMain.setVisibility(View.VISIBLE);
            fragmentHomeBinding.setIsLoading(false);
            if (tvResponse != null) {
                if (tvResponse.getResults() != null) {
                    tvModels.addAll(tvResponse.getResults());
                    tvAdapterMain.notifyDataSetChanged();
                }
            }
        });
    }

    private void getPopularMovie() {
        movieViewModel.getPopularMovie("6336e4208132f6206aa0b05d04b1fda7", 1).observe(getViewLifecycleOwner(), movieResponse -> {
            if (movieResponse != null) {
                if (movieResponse.getResults() != null) {
                    movieModels.addAll(movieResponse.getResults());
                    movieAdapterMain.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onTVClicked(TVModel tvModel) {
        Intent intent = new Intent(getContext(), TVDetailsActivity.class);
        intent.putExtra("id", tvModel.getId());
        intent.putExtra("name", tvModel.getName());
        intent.putExtra("original_name", tvModel.getOriginalName());
        intent.putExtra("language", tvModel.getOriginalLanguage());
        intent.putExtra("date", tvModel.getFirstAirDate());
        startActivity(intent);
    }

    @Override
    public void onMovieClicked(MovieModel movieModel) {

    }
}