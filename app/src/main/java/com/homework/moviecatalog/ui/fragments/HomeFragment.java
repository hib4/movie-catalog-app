package com.homework.moviecatalog.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;

import com.homework.moviecatalog.BuildConfig;
import com.homework.moviecatalog.databinding.FragmentHomeBinding;
import com.homework.moviecatalog.models.MovieModel;
import com.homework.moviecatalog.models.TVModel;
import com.homework.moviecatalog.ui.activities.MoreMovieActivity;
import com.homework.moviecatalog.ui.activities.MoreTVActivity;
import com.homework.moviecatalog.ui.activities.MovieDetailsActivity;
import com.homework.moviecatalog.ui.activities.TVDetailsActivity;
import com.homework.moviecatalog.ui.adapters.MovieAdapterMain;
import com.homework.moviecatalog.ui.adapters.SliderAdapter;
import com.homework.moviecatalog.ui.adapters.TVAdapterMain;
import com.homework.moviecatalog.ui.listeners.MovieListener;
import com.homework.moviecatalog.ui.listeners.TVListener;
import com.homework.moviecatalog.viewmodels.MovieViewModel;
import com.homework.moviecatalog.viewmodels.TVViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements TVListener, MovieListener {

    private FragmentHomeBinding binding;

    private TVViewModel tvViewModel;
    private List<TVModel> tvModels = new ArrayList<>();
    private TVAdapterMain tvAdapterMain;

    private MovieViewModel movieViewModel;
    private List<MovieModel> movieModels = new ArrayList<>();
    private MovieAdapterMain movieAdapterMain;

    private SliderAdapter sliderAdapter;

    public HomeFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        initializationTV();
        initializationMovie();
        initializationViewPager();

        return binding.getRoot();
    }

    private void initializationTV() {
        binding.rvListTvMain.setHasFixedSize(true);
        binding.rvListTvMain.setItemViewCacheSize(20);
        binding.rvListTvMain.setDrawingCacheEnabled(true);
        binding.rvListTvMain.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        tvViewModel = new ViewModelProvider(this).get(TVViewModel.class);
        tvAdapterMain = new TVAdapterMain(tvModels, this);
        binding.rvListTvMain.setAdapter(tvAdapterMain);
        binding.ivMoreTvMain.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), MoreTVActivity.class);
            startActivity(intent);
        });
        getPopularTV();
    }

    private void initializationMovie() {
        binding.rvListMovieMain.setHasFixedSize(true);
        binding.rvListMovieMain.setItemViewCacheSize(20);
        binding.rvListMovieMain.setDrawingCacheEnabled(true);
        binding.rvListMovieMain.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        movieAdapterMain = new MovieAdapterMain(movieModels, this);
        binding.rvListMovieMain.setAdapter(movieAdapterMain);
        binding.ivMoreMovieMain.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), MoreMovieActivity.class);
            startActivity(intent);
        });

        getPopularMovie();
    }

    private void getPopularTV() {
        binding.rlMain.setVisibility(View.GONE);
        binding.setIsLoading(true);
        tvViewModel.getPopularTVShows(BuildConfig.API_KEY, 1).observe(getViewLifecycleOwner(), tvResponse -> {
            binding.rlMain.setVisibility(View.VISIBLE);
            binding.setIsLoading(false);
            if (tvResponse != null) {
                if (tvResponse.getResults() != null) {
                    tvModels.addAll(tvResponse.getResults());
                    tvAdapterMain.notifyDataSetChanged();
                }
            }
        });
    }

    private void getPopularMovie() {
        movieViewModel.getPopularMovie(BuildConfig.API_KEY, 1).observe(getViewLifecycleOwner(), movieResponse -> {
            if (movieResponse != null) {
                if (movieResponse.getResults() != null) {
                    movieModels.addAll(movieResponse.getResults());
                    movieAdapterMain.notifyDataSetChanged();
                    sliderAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void initializationViewPager() {
        sliderAdapter = new SliderAdapter(movieModels, binding.viewPager);
        binding.viewPager.setAdapter(sliderAdapter);
        binding.viewPager.setClipToPadding(false);
        binding.viewPager.setClipChildren(false);
        binding.viewPager.setOffscreenPageLimit(3);
        binding.viewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        binding.viewPager.setPageTransformer(compositePageTransformer);
    }

    @Override
    public void onTVClicked(TVModel tvModel) {
        Intent intent = new Intent(getContext(), TVDetailsActivity.class);
        intent.putExtra("tv", tvModel);
        startActivity(intent);
    }

    @Override
    public void onMovieClicked(MovieModel movieModel) {
        Intent intent = new Intent(getContext(), MovieDetailsActivity.class);
        intent.putExtra("movie", movieModel);
        startActivity(intent);
    }

}