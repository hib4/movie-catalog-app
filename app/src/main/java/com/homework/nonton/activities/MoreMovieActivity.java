package com.homework.nonton.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.homework.nonton.R;
import com.homework.nonton.adapters.MovieAdapterMore;
import com.homework.nonton.databinding.ActivityMoreMovieBinding;
import com.homework.nonton.listeners.MovieListener;
import com.homework.nonton.models.MovieModel;
import com.homework.nonton.viewmodels.MovieViewModel;

import java.util.ArrayList;
import java.util.List;

public class MoreMovieActivity extends AppCompatActivity implements MovieListener {

    private ActivityMoreMovieBinding activityMoreMovieBinding;
    private MovieViewModel movieViewModel;
    private List<MovieModel> movieModels = new ArrayList<>();
    private MovieAdapterMore movieAdapterMore;
    private int currentPage = 1;
    private int totalAvailablePages = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMoreMovieBinding = DataBindingUtil.setContentView(this, R.layout.activity_more_movie);
        doInitialization();
    }

    private void doInitialization() {
        activityMoreMovieBinding.rvListMoreMovie.setHasFixedSize(true);
        activityMoreMovieBinding.rvListMoreMovie.setItemViewCacheSize(20);
        activityMoreMovieBinding.rvListMoreMovie.setDrawingCacheEnabled(true);
        activityMoreMovieBinding.rvListMoreMovie.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        movieAdapterMore = new MovieAdapterMore(movieModels, this);
        activityMoreMovieBinding.rvListMoreMovie.setAdapter(movieAdapterMore);
        activityMoreMovieBinding.rvListMoreMovie.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!activityMoreMovieBinding.rvListMoreMovie.canScrollVertically(1)) {
                    if (currentPage <= totalAvailablePages) {
                        currentPage += 1;
                        getPopularMovie();
                    }
                }
            }
        });
        activityMoreMovieBinding.ivBackMoreMovie.setOnClickListener(view -> {
            onBackPressed();
        });
        getPopularMovie();
    }

    private void getPopularMovie() {
        toggleLoading();
        movieViewModel.getPopularMovie("6336e4208132f6206aa0b05d04b1fda7", currentPage).observe(this, movieResponse -> {
            toggleLoading();
            if (movieResponse != null) {
                totalAvailablePages = movieResponse.getTotalPages();
                if (movieResponse.getResults() != null) {
                    int oldCount = movieModels.size();
                    movieModels.addAll(movieResponse.getResults());
                    movieAdapterMore.notifyItemRangeInserted(oldCount, movieModels.size());
                }
            }
        });
    }

    private void toggleLoading() {
        if (currentPage == 1) {
            if (activityMoreMovieBinding.getIsLoading() != null && activityMoreMovieBinding.getIsLoading()) {
                activityMoreMovieBinding.setIsLoading(false);
            } else {
                activityMoreMovieBinding.setIsLoading(true);
            }
        } else {
            if (activityMoreMovieBinding.getIsLoadingMore() != null && activityMoreMovieBinding.getIsLoadingMore()) {
                activityMoreMovieBinding.setIsLoadingMore(false);
            } else {
                activityMoreMovieBinding.setIsLoadingMore(true);
            }
        }
    }

    @Override
    public void onMovieClicked(MovieModel movieModel) {
        Intent intent = new Intent(getApplicationContext(), MovieDetailsActivity.class);
        intent.putExtra("id", movieModel.getId());
        intent.putExtra("name", movieModel.getTitle());
        intent.putExtra("original_name", movieModel.getOriginalTitle());
        intent.putExtra("language", movieModel.getOriginalLanguage());
        intent.putExtra("date", movieModel.getReleaseDate());
        startActivity(intent);
    }
}