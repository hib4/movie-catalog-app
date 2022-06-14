package com.homework.nonton.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.homework.nonton.BuildConfig;
import com.homework.nonton.R;
import com.homework.nonton.ui.adapters.MovieAdapterMore;
import com.homework.nonton.databinding.ActivityMoreMovieBinding;
import com.homework.nonton.ui.listeners.MovieListener;
import com.homework.nonton.models.MovieModel;
import com.homework.nonton.viewmodels.MovieViewModel;

import java.util.ArrayList;
import java.util.List;

public class MoreMovieActivity extends AppCompatActivity implements MovieListener {

    private ActivityMoreMovieBinding binding;
    private MovieViewModel viewModel;
    private List<MovieModel> movieModels = new ArrayList<>();
    private MovieAdapterMore movieAdapterMore;
    private int currentPage = 1;
    private int totalAvailablePages = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_more_movie);
        initialization();
    }

    private void initialization() {
        binding.rvListMoreMovie.setHasFixedSize(true);
        binding.rvListMoreMovie.setItemViewCacheSize(20);
        binding.rvListMoreMovie.setDrawingCacheEnabled(true);
        binding.rvListMoreMovie.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        viewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        movieAdapterMore = new MovieAdapterMore(movieModels, this);
        binding.rvListMoreMovie.setAdapter(movieAdapterMore);
        binding.rvListMoreMovie.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!binding.rvListMoreMovie.canScrollVertically(1)) {
                    if (currentPage <= totalAvailablePages) {
                        currentPage += 1;
                        getPopularMovie();
                    }
                }
            }
        });
        binding.ivBackMoreMovie.setOnClickListener(view -> onBackPressed());
        binding.ivSearchMoreMovie.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), MovieSearchActivity.class)));
        getPopularMovie();
    }

    private void getPopularMovie() {
        toggleLoading();
        viewModel.getPopularMovie(BuildConfig.API_KEY, currentPage).observe(this, movieResponse -> {
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
            if (binding.getIsLoading() != null && binding.getIsLoading()) {
                binding.setIsLoading(false);
            } else {
                binding.setIsLoading(true);
            }
        } else {
            if (binding.getIsLoadingMore() != null && binding.getIsLoadingMore()) {
                binding.setIsLoadingMore(false);
            } else {
                binding.setIsLoadingMore(true);
            }
        }
    }

    @Override
    public void onMovieClicked(MovieModel movieModel) {
        Intent intent = new Intent(getApplicationContext(), MovieDetailsActivity.class);
        intent.putExtra("movie", movieModel);
        startActivity(intent);
    }
}